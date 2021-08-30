package com.aimers.zone.Modals;

public class OfferModal {
    String heading,body,match_id;

    public OfferModal(String heading, String body, String match_id) {
        this.heading = heading;
        this.body = body;
        this.match_id = match_id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
