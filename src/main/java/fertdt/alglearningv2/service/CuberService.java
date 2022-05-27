package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.request.CuberLoginRequest;
import fertdt.alglearningv2.dto.request.CuberRegistrationRequest;
import fertdt.alglearningv2.dto.response.CuberResponse;
import fertdt.alglearningv2.dto.response.ExceptionResponse;
import fertdt.alglearningv2.model.CuberEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CuberService extends UserDetailsService {
    void save(CuberEntity cuber);

    CuberEntity getUnknownUser();

    CuberEntity getUserByNickname(String nickname);

    List<ExceptionResponse> register(CuberRegistrationRequest cuberRegistrationRequest);

    void delete(CuberEntity cuber);

    CuberResponse getCuberResponse(CuberEntity cuber);

    List<ExceptionResponse> loginAttempt(CuberLoginRequest cuberLoginRequest);

    void updateLastVisited(CuberEntity cuber);
}
