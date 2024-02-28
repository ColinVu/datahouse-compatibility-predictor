import java.io.FileNotFoundException; 
import java.io.PrintWriter; 
import java.util.LinkedHashMap; 
import java.util.Map; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is the main file to run the compatibility predictor! Here's how it works:
 * 
 * To run the file, make sure you have the JRE or JDK installed, with any relatively modern version. Note that these instructions are for running the program with Windows.
 * 
 * Within this directory is the json.simple library, which allows you to read JSON files in Java. If it does not work (which is unlikely), then add it as a classpath to the system environment.
 * 
 * Run the following command from the command line within the directory to compile the files: javac -cp "json-simple-1.1.jar" *.java
 * 
 * Now, run the following command to run the file: java -cp "json-simple-1.1.jar" Main.java
 * 
 * The outfile file with the results should be in the file marked as "output.java".
 * 
 * To enter your preferred json file into the system to run the program, change the text in the string on the FIRST LINE OF THE MAIN METHOD to the preferred file name.
 */

@SuppressWarnings("unchecked")
public class Main 
{ 
	public static void main(String[] args) throws FileNotFoundException 
	{
		String fileName = "input.json";

		// Let's parse the input file and put it in a JSONObject.

		JSONParser jparser = new JSONParser();
		JSONObject initialObject = new JSONObject();
		JSONObject returnObject = new JSONObject();
		JSONArray scoredApplicants = new JSONArray();
        try (FileReader reader = new FileReader(fileName))
		{
            initialObject = (JSONObject) jparser.parse(reader);

			// Now, the information from the input file has been parsed. Let's store it in our own data structure to skim it for information.
			
			JSONArray teamArray = (JSONArray) initialObject.get("team");
			JSONArray applicantArray = (JSONArray) initialObject.get("applicants");

			// Let's evaluate the existing candidates to identify the needs for our group.

			boolean hasLowIntel = false;
			long totalEndurance = 0;
			long totalSpiceTolerance = 0;

			for (Object item : teamArray) {
				JSONObject temp = (JSONObject)(((JSONObject)(item)).get("attributes"));
				if ((Long)(temp.get("intelligence")) < 4.0) {
					hasLowIntel = true;
				}
				totalEndurance += (Long)(temp.get("endurance"));
				totalSpiceTolerance += (Long)(temp.get("spicyFoodTolerance"));
			}

			// We now know the current needs of the group. Let's see how the new applicants compare to that.

			for (Object item : applicantArray) {
				JSONObject newObject = new JSONObject();
				double objectScore = 1.0;
				double intelScore = 1.0;
				double strengthScore = 1.0;
				double enduranceScore = 1.0;
				double spiceScore = 1.0;

				JSONObject temp = (JSONObject)(((JSONObject)(item)).get("attributes"));
				double curr = ((Long)(temp.get("intelligence"))).doubleValue();
				if (curr >= 4.0) {
					intelScore = 0.5 + (curr * 0.05);
				} else {
					if (hasLowIntel) {
						intelScore = 0.4 + (curr * 0.1);
					} else {
						intelScore = 0.65;
					}
				}

				curr = ((Long)(temp.get("strength"))).doubleValue();
				if (curr < 1.5) {
					strengthScore = 0.6;
				} else if (curr < 2.5) {
					strengthScore = 0.7;
				} else if (curr >= 9.5) {
					strengthScore = 1.0;
				} else if (curr >= 8.5) {
					strengthScore = 0.95;
				} else {
					strengthScore = 0.75 + (0.025 * (curr - 2.0));
				}

				curr = ((Long)(temp.get("endurance"))).doubleValue();
				if ((double)totalEndurance / (double)(teamArray.size()) < 5.0) {
					enduranceScore = 0.5 + (curr * 0.05);
				} else {
					enduranceScore = 0.6 + (curr * 0.04);
				}

				curr = ((Long)(temp.get("spicyFoodTolerance"))).doubleValue();
				if ((double)totalSpiceTolerance / (double)(teamArray.size()) < 5.0) {
					spiceScore = 0.75 + (curr * 0.025);
				} else if ((double)totalSpiceTolerance / (double)(teamArray.size()) > 8.0) {
					spiceScore = 1.0 - (curr * 0.01);
				} else {
					spiceScore = 0.9 + (curr * 0.01);
				}

				objectScore = intelScore * strengthScore * enduranceScore * spiceScore;
				if (objectScore >= 1.0) {
					objectScore = 0.999;
				}
				objectScore = (double)((int)(objectScore * 1000.0)) / 1000.0;
				newObject.put("name", ((JSONObject)(item)).get("name"));
				newObject.put("score", objectScore);
				scoredApplicants.add(newObject);
			}


		}
		catch (IOException | ParseException e)
		{
			System.out.println("Invalid input file");
		}
		
		// Finally, let's write our compatibility scores into a new file.

		returnObject.put("scoredApplicants", scoredApplicants);
		PrintWriter jwriter = new PrintWriter("output.json"); 
		jwriter.write(returnObject.toJSONString()); 
		jwriter.flush(); 
		jwriter.close(); 
	} 
}