package io.getarrays.server.implementations;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.server.enumerations.Status;
import io.getarrays.server.models.Server;
import io.getarrays.server.repositories.ServerRepo;
import io.getarrays.server.services.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor // crea el constructor e inyecta la dependency
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
  
  private final ServerRepo serverRepo;
  
  @Override
  public Server create(Server server) {
    log.info("Saving new Server: {}",server.getName());
    server.setImageUrl(setServerImageUrl());
    return serverRepo.save(server);
  }
  
  @Override
  public Server ping(String ipAddress) throws IOException {
    log.info("Pinging Server IP: {}", ipAddress);
    // primero lo busco,despues le hago ping
    Server server = serverRepo.findByIpAddress(ipAddress);
    InetAddress inetAddress = InetAddress.getByName(ipAddress);
    // InetAddress.isReachable(timeout máximo)
    server.setStatus(inetAddress.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
    // tras cambiarle el estado lo guardo
    serverRepo.save(server);
    return server;
  }
  
  @Override
  public Collection<Server> list(int limit) {
    log.info("Fetching all servers with limit: {}", limit);
    return serverRepo.findAll(PageRequest.of(0,limit)).toList();
  }
  
  @Override
  public Server get(Long id) {
    log.info("Fetching server with id: {}",id);
    return serverRepo.findById(id).get();
  }
  
  @Override
  public Server update(Server server) {
    log.info("Updating server with name: {}",server.getName());
    // JPA es lo suficientemente listo para guardarlo sobre el que ya está
    return serverRepo.save(server);
  }
  
  @Override
  public Boolean delete(Long id) {
    log.info("Deleting server with id: {}",id);
    serverRepo.deleteById(id);
    return Boolean.TRUE;
  }
  
  
  private String setServerImageUrl() {
    String[] imageNames = {"server1.png","server2.png","server3.png","server4.png"};
    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/images/"+ imageNames[new Random().nextInt(4)]).toUriString();
  }
}
