package com.dev.server.service;

import com.dev.server.model.Server;
import com.dev.server.repo.ServerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.dev.server.enumaration.Status.SERVER_DOWN;
import static com.dev.server.enumaration.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepo serverRepo;
    @Override
    public Server create(Server server) {
        log.info("Saving new server:{}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP:{}",ipAddress);
        Server server=serverRepo.findByIpAddress(ipAddress);
        InetAddress address= InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? SERVER_UP:SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id");
        if(serverRepo.findById(id).isPresent()){
            return serverRepo.findById(id).get();
        }
        else throw new RuntimeException();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server:{}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id:{}",id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }
    private String setServerImageUrl() {
        String[] imageNames={"server1.png","server2.png","server3.png","server4.png"};

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("server/image/"+imageNames[new Random().nextInt(4)]).toUriString();
    }
}

