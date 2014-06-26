package org.kuali.coeus.common.api.specialreview;


import java.sql.Date;

public interface SpecialReviewContract {

    Integer getSpecialReviewNumber();

    String getProtocolNumber();

    Date getApplicationDate();

    Date getApprovalDate();

    Date getExpirationDate();

    String getComments();

    String getProtocolStatus();

    SpecialReviewTypeContract getSpecialReviewType();

    SpecialReviewApprovalTypeContract getApprovalType();
}
