package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
public class User {
	@Id
	private String id;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	@Email
	private String email;

	@Version
	private int version;

	public User() {
	}

	public User(String id, @NotNull String password, @NotNull String name, @NotNull @Email String email) {
		this.id = id;
		this.password = hashPassword(password);
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static String hashPassword(String password) {
		char[] encoded = null;
		try {
			ByteBuffer passwdBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(password));
			byte[] passwdBytes = passwdBuffer.array();
			MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
			mdEnc.update(passwdBytes, 0, password.toCharArray().length);
			encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
		}
		return new String(encoded);
	}
}
