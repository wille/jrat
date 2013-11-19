package pro.jrat.client.listeners;

public abstract class DownloadListener {

	public abstract void fail(String msg);

	public abstract void done(String msg);

	public abstract void reportProgress(int i, int total);

}
