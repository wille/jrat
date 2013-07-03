package pro.jrat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pro.jrat.net.WebRequest;

public class Contributors {
	
	private static final List<Contributor> CONTRIBUTORS = new ArrayList<Contributor>();
	private static final List<Donator> DONATORS = new ArrayList<Donator>();
	
	public List<Contributor> getContributors() {
		if (CONTRIBUTORS.size() == 0) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/misc/contributors.php?contributors")));
				String name;
				
				while ((name = reader.readLine()) != null) {
					String country = reader.readLine();
					String reason = reader.readLine();
					Contributor contributor = new Contributor(name, country, reason);
					CONTRIBUTORS.add(contributor);
				}
				
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return CONTRIBUTORS;
	}
	
	public List<Donator> getDonators() {
		if (DONATORS.size() == 0) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/misc/contributors.php?donators")));
				String name;
				
				while ((name = reader.readLine()) != null) {
					String country = reader.readLine();
					String reason = reader.readLine();
					Donator donator = new Donator(name, country, reason);
					DONATORS.add(donator);
				}
				
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return DONATORS;
	}

	public class Contributor extends AbstractContributor {

		public Contributor(String name, String country, String reason) {
			super(name, country, reason);
		}
	}
	
	public class Donator extends AbstractContributor {

		public Donator(String name, String country, String reason) {
			super(name, country, reason);
		}
	}
	
	public abstract class AbstractContributor {
		
		private final String name;
		private final String country;
		private final String reason;
		
		public AbstractContributor(String name, String country, String reason) {
			this.name = name;
			this.country = country;
			this.reason = reason;
		}

		public String getReason() {
			return reason;
		}

		public String getName() {
			return name;
		}

		public String getCountry() {
			return country;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof AbstractContributor) {
				return getName().equals(((AbstractContributor)o).getName());
			} 
			
			return false;
		}

	}
}


