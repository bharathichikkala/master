package com.zone.zissa.audit;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * ExposeAttemptedPathAuthorizationAuditListener class.
 *
 * @see ExposeAttemptedPathAuthorizationAuditEvent
 */
@Component
public class ExposeAttemptedPathAuthorizationAuditListener
    extends AbstractAuthorizationAuditListener {

  /** The Constant AUTHORIZATION_FAILURE. */
  public static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

  /**
   * On application event.
   *
   * @param event the event
   */
  @Override
  public void onApplicationEvent(final AbstractAuthorizationEvent event) {
    if (event instanceof AuthorizationFailureEvent) {
      onAuthorizationFailureEvent((AuthorizationFailureEvent) event);
    }
  }

  /**
   * On authorization failure event.
   *
   * @param event the event
   */
  private void onAuthorizationFailureEvent(
      final AuthorizationFailureEvent event) {
    Map<String, Object> data = new HashMap<>();
    data.put("type", event.getAccessDeniedException().getClass().getName());
    data.put("message", event.getAccessDeniedException().getMessage());
    data.put("requestUrl",
        ((FilterInvocation) event.getSource()).getRequestUrl());
    if (event.getAuthentication().getDetails() != null) {
      data.put("details", event.getAuthentication().getDetails());
    }
    publish(new AuditEvent(event.getAuthentication().getName(),
        AUTHORIZATION_FAILURE, data));
  }
}
