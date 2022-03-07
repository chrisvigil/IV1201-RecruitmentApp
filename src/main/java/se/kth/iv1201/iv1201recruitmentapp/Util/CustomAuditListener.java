package se.kth.iv1201.iv1201recruitmentapp.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listeners for logging events
 */
@Component
public class CustomAuditListener {
    private Logger logger = LoggerFactory.getLogger("security");

    /**
     * Logging of authorization and authentication events from spring security.
     * @param event event to listen to
     */
    @EventListener
    public void onAuditEvent(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        logger.info("type={}, principal={}, data={}", auditEvent.getType(), auditEvent.getPrincipal(), auditEvent.getData());
    }
}
