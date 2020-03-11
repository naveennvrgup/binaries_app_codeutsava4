package binaries.app.codeutsava.restapi.model.auth;

import java.io.Serializable;

public class SignupPayload implements Serializable {
    public String name;
    public String password;
    public String contact;
    public String address="some address";
    public String city="some city";
    public String state="some state";
    public String role="some role";
    public String dob="2000-01-01";
    public String adhaar="some adhaar";
}
