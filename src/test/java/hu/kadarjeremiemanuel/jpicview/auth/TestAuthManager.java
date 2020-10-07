package hu.kadarjeremiemanuel.jpicview.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestAuthManager {
	private AuthManager am;

	@BeforeEach
	void setUp() throws Exception {
		am = AuthManager.getInstance();
	}
	
	@Test
	public void testLogin() {
		assertTrue(am.login("guest", "guest"));
		assertTrue(am.isAuth());
		assertTrue(am.checkRole(RolesAndPermissions.GUEST));
		assertTrue(am.checkPermission("png:view"));
	}
	
	@Test
	public void testLogout() {
		assertTrue(am.login("guest", "guest"));
		assertTrue(am.isAuth());
		am.logout();
		assertFalse(am.isAuth());
	}
	
	@Test
    public void testSessionSetAndGet()
    {
		assertTrue(am.login("guest", "guest"));
		assertTrue(am.isAuth());
		am.setSessVal("example", String.valueOf(4.21d));
		double exampleVal = Double.parseDouble(am.getSessVal("example"));
		assertEquals(4.21d, exampleVal);
    }
}
