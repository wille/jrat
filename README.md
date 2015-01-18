# jRAT Stub API
API for developing stub-side plugins for jRAT
## How to start using it
You need to add it as a library in your IDE. You WILL need to include it in your output JAR, jRAT Stub will NOT have it loaded.
## Structure
Both your Controller and Stub plugin need a plugin.txt file in the root that will contain the main class name (like se.jrat.MainClass)