package io.getarrays.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.server.models.Server;

// fijate que Server es el domain o clase
public interface ServerRepo extends JpaRepository<Server, Long> {
  Server findByIpAddress(String ipAddress);
}
