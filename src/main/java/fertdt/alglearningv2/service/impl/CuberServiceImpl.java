package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.dto.request.CuberLoginRequest;
import fertdt.alglearningv2.dto.request.CuberRegistrationRequest;
import fertdt.alglearningv2.dto.response.CuberResponse;
import fertdt.alglearningv2.dto.response.ExceptionResponse;
import fertdt.alglearningv2.exception.CuberNotFoundException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.repository.CuberRepository;
import fertdt.alglearningv2.repository.RoleRepository;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.util.PasswordUtil;
import fertdt.alglearningv2.util.mapper.CuberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CuberServiceImpl implements CuberService {
    private final CuberRepository cuberRepository;
    private final RoleRepository roleRepository;
    private final CuberMapper cuberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(CuberEntity cuber) {
        cuberRepository.save(cuber);
    }

    @Override
    public CuberEntity getUnknownUser() {
        return cuberRepository.findByNickname("unknown_user").get();
    }

    @Override
    public CuberEntity getUserByNickname(String nickname) {
        return cuberRepository.findByNickname(nickname).orElseThrow(CuberNotFoundException::new);
    }

    @Override
    public List<ExceptionResponse> register(CuberRegistrationRequest cuberRegistrationRequest) {
        List<ExceptionResponse> exceptions = new ArrayList<>();
        if (cuberRepository.findByNickname(cuberRegistrationRequest.getNickname()).isPresent())
            exceptions.add(new ExceptionResponse("nicknameMatch", "Пользователь с таким никнеймом уже существует"));
        if (cuberRepository.findByEmail(cuberRegistrationRequest.getEmail()).isPresent())
            exceptions.add(new ExceptionResponse("emailMatch", "Эта почта уже привязана к аккаунту " +
                    "другого пользователя"));
        if (!PasswordUtil.passwordCorrect(cuberRegistrationRequest.getPassword())) exceptions.add(new ExceptionResponse
                ("weakPassword", "Пароль должен быть не менее 8 символов в длину и содержать строчную, " +
                        "заглавную букву и цифру"));
        if (!cuberRegistrationRequest.getPassword().equals(cuberRegistrationRequest.getConfirmPassword()))
            exceptions.add(new ExceptionResponse("passwordMatch", "Пароли не совпадают"));
        if (exceptions.isEmpty()) {
            CuberEntity cuber = cuberMapper.toEntity(cuberRegistrationRequest);
            cuber.setAuthorities(Set.of(roleRepository.findByName("ROLE_USER").get()));
            cuberRepository.save(cuber);
        }
        return exceptions;
    }

    @Override
    public void delete(CuberEntity cuber) {
        cuberRepository.delete(cuber);
    }

    @Override
    public CuberResponse getCuberResponse(CuberEntity cuber) {
        return cuberMapper.toResponse(cuber);
    }

    @Override
    public List<ExceptionResponse> loginAttempt(CuberLoginRequest cuberLoginRequest) {
        List<ExceptionResponse> exceptions = new ArrayList<>();
        Optional<CuberEntity> cuberOptional = cuberRepository.findByNickname(cuberLoginRequest.getNickname());
        if (cuberOptional.isEmpty())
            exceptions.add(new ExceptionResponse("noSuchUser", "Пользователя с таким никнеймом не существует"));
        else {
            CuberEntity cuber = cuberOptional.get();
            if (!passwordEncoder.matches(cuberLoginRequest.getPassword(), cuber.getHashedPassword()))
                exceptions.add(new ExceptionResponse("incorrectPassword", "Неправильный пароль"));
        }
        return exceptions;
    }

    @Override
    public void updateLastVisited(CuberEntity cuber) {
        if (!cuber.getLastVisited().equals(LocalDate.now())) {
            cuber.setVisitedDays(cuber.getVisitedDays() + 1);
            if (cuber.getLastVisited().equals(LocalDate.now().minus(1, ChronoUnit.DAYS)))
                cuber.setVisitedDaysRow(cuber.getVisitedDaysRow() + 1);
            else cuber.setVisitedDaysRow(1);
            cuber.setLastVisited(LocalDate.now());
            save(cuber);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cuberRepository.findByNickname(username).orElseThrow(CuberNotFoundException::new);
    }
}
