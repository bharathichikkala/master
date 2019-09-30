package com.zone.zissa.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.listener.AbstractAuditListener;
import org.springframework.stereotype.Component;

/** AuditEventListener class. */
@Component
public class AuditEventListener extends AbstractAuditListener {

  /** The Constant LOG. */
  private static final Logger LOG =
      LoggerFactory.getLogger(AuditEventListener.class);

  /** The audit event repository. */
  @Autowired
  private AuditEventRepository auditEventRepository;

  /**
   * The onAuditEvent method.
   *
   * @param event the event
   */
  @Override
  public void onAuditEvent(final AuditEvent event) {

    LOG.info("On audit event: timestamp: {}, principal: {}, type: {}, data: {}",
        event.getTimestamp(), event.getPrincipal(), event.getType(),
        event.getData());

    auditEventRepository.add(event);
  }
}
