package university.Security;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtConfig {
	public static final String header="Authorization";
	public static final String prefix="Bearer ";
	public static final String secret="asdcghcuchdurugthahsdngytieoshgynwbeydyenddcykasdcghcuchdurugthahsdngytieoshgynwbethahsdngytieoshgynwbe";
	public static final int expiration=1000000000;
}
