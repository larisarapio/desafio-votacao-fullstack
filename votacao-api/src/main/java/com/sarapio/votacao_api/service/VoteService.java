package com.sarapio.votacao_api.service;

import com.sarapio.votacao_api.domain.associate.Associate;
import com.sarapio.votacao_api.domain.session.Session;
import com.sarapio.votacao_api.domain.vote.Vote;
import com.sarapio.votacao_api.domain.vote.VoteRequestDTO;
import com.sarapio.votacao_api.repositories.AssociateRepository;
import com.sarapio.votacao_api.repositories.SessionRepository;
import com.sarapio.votacao_api.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final AssociateRepository associateRepository;
    private final SessionRepository sessionRepository;

    public VoteService(VoteRepository voteRepository, AssociateRepository associateRepository, SessionRepository sessionRepository) {
        this.voteRepository = voteRepository;
        this.associateRepository = associateRepository;
        this.sessionRepository = sessionRepository;
    }


    public String createVote(UUID associateId, UUID sessionId, VoteRequestDTO data) {
        Associate associate = associateRepository.findById(associateId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new IllegalArgumentException("Session not found"));

        Vote votoExistente = voteRepository.findByAssociateAndSession(associate, session);
        if (votoExistente != null) {
            return "Você já votou nesta sessão!";
        }

        Vote voto = new Vote();
        voto.setValue(data.value());
        voto.setAssociate(associate);
        voto.setSession(session);
        voteRepository.save(voto);

        session.setCountVoto(session.getCountVoto() + 1);
        sessionRepository.save(session);

        return "Voto registrado com sucesso!";
    }

    public List<Session> results() {
        return sessionRepository.findAll();
    }

}
