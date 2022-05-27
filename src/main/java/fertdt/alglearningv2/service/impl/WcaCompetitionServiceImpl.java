package fertdt.alglearningv2.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fertdt.alglearningv2.constants.UriConstants;
import fertdt.alglearningv2.dto.response.WcaCompetitionResponse;
import fertdt.alglearningv2.exception.MapboxException;
import fertdt.alglearningv2.exception.WcaException;
import fertdt.alglearningv2.model.WcaCompetitionEntity;
import fertdt.alglearningv2.repository.WcaCompetitionRepository;
import fertdt.alglearningv2.service.WcaCompetitionService;
import fertdt.alglearningv2.util.mapper.WcaCompetitionMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WcaCompetitionServiceImpl implements WcaCompetitionService {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final WcaCompetitionRepository wcaCompetitionRepository;
    private final WcaCompetitionMapper wcaCompetitionMapper;

    @Value("${mapbox.access-token}")
    private String mapboxAccessToken;

    @Override
    public void updateAllUpcomingCompetitions() {
        Request request = new Request.Builder()
                .url(UriConstants.WCA_COMPETITIONS_PAGE_URI)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String html = response.body().string();
            String[] competitions = html.split("<div class=\"competition-link\">");
            List<WcaCompetitionEntity> wcaCompetitionEntities = new ArrayList<>();
            for (String competition : competitions) {
                if (competition.equals(competitions[0])) continue;
                String link = competition.split("<a href=\"/competitions")[1].split("\">")[0];
                String name = competition.split("<a href=\"/competitions")[1].split("\">")[1].split("</a>")[0].strip();
                String uri = UriConstants.WCA_COMPETITIONS_PAGE_URI + link;
                Request competitionRequest = new Request.Builder()
                        .url(uri)
                        .build();
                call = client.newCall(competitionRequest);
                response = call.execute();
                String coordinates = response.body().string().split("href=\"https://www\\.google\\.com/maps/place/")[1].split("\">")[0];
                wcaCompetitionEntities.add(WcaCompetitionEntity.builder()
                        .name(name)
                        .link(uri)
                        .coordinates(coordinates)
                        .build());
            }
            wcaCompetitionRepository.deleteAll();
            wcaCompetitionRepository.saveAll(wcaCompetitionEntities);
        } catch (IOException e) {
            throw new WcaException("Problem with get wca page");
        }
    }

    @Override
    public List<WcaCompetitionResponse> getAllUpcomingCompetitions() {
        return wcaCompetitionMapper.toResponse(wcaCompetitionRepository.findAll());
    }

    @Override
    public List<WcaCompetitionResponse> getNearestCompetitions(String coordinates) {
        List<WcaCompetitionResponse> allCompetitions = getAllUpcomingCompetitions();
        allCompetitions.forEach(a -> a.setDistance(String.valueOf(distance(
                Double.parseDouble(a.getCoordinates().split(",")[0]),
                Double.parseDouble(a.getCoordinates().split(",")[1]),
                Double.parseDouble(coordinates.split(",")[1]),
                Double.parseDouble(coordinates.split(",")[0]))).split("\\.")[0]));
        allCompetitions.sort(Comparator.comparingDouble(a -> Double.parseDouble(a.getDistance())));
        allCompetitions = allCompetitions.stream().limit(5).collect(Collectors.toList());
        for (WcaCompetitionResponse wcaCompetition : allCompetitions) {
            String uri = UriConstants.MAPBOX_API_DISTANCE_URI;
            uri = uri.replace("{from}", coordinates);
            uri = uri.replace("{to}", swapCoordinates(wcaCompetition.getCoordinates()));
            uri = uri.replace("{access-token}", mapboxAccessToken);
            Request request = new Request.Builder()
                    .url(uri)
                    .build();
            Call call = client.newCall(request);
            try {
                String json = (call.execute().body().string());
                try {
                    wcaCompetition.setDistanceByCar(String.valueOf(objectMapper.readTree(json).get("distances").get(0).get(0).asDouble() / 1000).split("\\.")[0]);
                } catch (JsonMappingException ignored) {
                }
            } catch (IOException e) {
                throw new MapboxException("Problem with mapbox api");
            }
        }

        return allCompetitions;
    }

    private String swapCoordinates(String coordinates) {
        String[] splitCoordinates = coordinates.split(",");
        return splitCoordinates[1] + "," + splitCoordinates[0];
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        var p = 0.017453292519943295;
        var a = 0.5 - Math.cos((lat2 - lat1) * p) / 2 +
                Math.cos(lat1 * p) * Math.cos(lat2 * p) *
                        (1 - Math.cos((lon2 - lon1) * p)) / 2;
        return 12742 * Math.asin(Math.sqrt(a));
    }
}
