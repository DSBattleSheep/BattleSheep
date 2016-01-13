package lobby.view;

public interface LobbyFrameObserver {

	public void onClientJoin(String username, String host, int port);
	
}
