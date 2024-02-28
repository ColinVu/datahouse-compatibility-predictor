# datahouse-compatibility-predictor
Hello! Here’s my explanation for my proposed compatibility predictor. It’s a bit lengthy since I wanted to thoroughly explain my thought process about choosing my weights, so there’s a TLDR on the bottom if you would prefer that.

I decided to program this in Java since it’s the language I’m most familiar with, specifically regarding professional formatting conventions and documentation. I’m aware that JavaScript might be more efficient at reading JSON files, since it’s embedded into the language. I’m using json.simple to read JSON files–while it should work fine since the jar file is included in the Git repo, let me know if you are unable to run it.

A Java Runtime Environment or Development Kit is necessary to run the files. If compiling is needed, then run the following command in the command line:

javac -cp "json-simple-1.1.jar" Main.java

To run the file, run the following command:

java -cp "json-simple-1.1.jar" Main.java

The program is currently reading from the file “input.json”. To change this, go to the first line in the main method. Changing the code would require recompiling the file. The output file is placed at “output.json”.

As a brief overview of the code, a parser turns the input JSON file into a JSONObject. I then divide the input file into team members and applicants. I parse through the JSONArray of team members to find data about them (described below) and then parse through the JSONArray of applicants to calculate their score. This score takes into account both the applicant’s attributes and the data collected about the current team members to identify fit. Each applicant is added to a new JSONObject, which is then written to the “output.json” file.

Since it’s an open-ended question about my personal thoughts evaluating team members, I decided to write my explanation as a bit of a story. As stated earlier, it’s quite lengthy, so there’s a TLDR of my weighing factors on the bottom.

It’s 1:00 AM. Bleak, buzzing office lights illuminate the desk space I’m in. Plain gray walls lead to high windows that solely reveal the darkness of night, devoid of stars. I stare down the empty row of computer monitors, and it’s almost a haunting feeling. A few hours ago, this scene was buzzing with life–a Mecca of conversation, chalk-full of young engineers and scientists discussing differential equations, chemical equations, and the new seasonal Dunkin’ Donuts offerings.

I’ve visited a number of university campuses and their libraries, but what’s unique to Georgia Tech–my place of study–is quite how lively its library environment is. The constant flow of people has given me the opportunity to meet and observe thousands of people and understand their group work habits. It’s allowed me to have a better understanding of what teams I want to be in and what I value in teams. That’s how I went about sorting between these four categories–intelligence, strength, endurance, and spicy food tolerance. Each statistic is given a “weighing factor,” each of which is multiplied together to form the final result.

Looking at that intelligence statistic, I’m wondering how it’s measured. I assume it’s a strict academic assessment, but that doesn’t necessarily account for business intelligence, or street smarts. Two spots to my left, two days ago, sat my friend Hudson, an exciting character who is potentially failing two classes right now. Beyond his GPA, he’s one of the most social people I’ve ever met. I’ve connected with countless figures that I consult during projects and exam crunches through simply being acquainted with him. Using sheer networking, he was able to host one of the best parties i’ve ever been to, all on a shoestring budget. Don’t get me wrong–qualifications to perform a task are essential to consider when adding someone to a team–but I don’t think a lack of “intelligence” per se should disqualify a candidate from consideration. There’s also roles in a group to be filled by workers who may have other unique skills. That’s how I formed this weighing factor: it’s equal to 0.5 + (intelligence * 0.05) for applicants whose intelligence is 4 or above. For applicants whose intelligence is 3 or below, if there already exists a member in the group whose intelligence is 3 or below, then the factor is 0.4 + (intelligence * 0.1). If not, then it’s set to a constant 0.65.

Secondly, strength. I like to think that the appearance of strength is being measured here: the power of confidence in a group dynamic is essential for accomplishing lofty goals. I’ve worked on projects where our whole group had an initial feeling of hopelessness, but through sheer dumb confidence we ended up making a final product better than any of us could’ve hoped. That was the story of every night before a robotics competition. Furthermore, it can prevent potentially disastrous situations from getting out of hand–working as an engineering teacher over the summer, we once had to call the emergency services when an eight year old innovator sprayed hot glue into his partner’s eye. The aroma of confidence from us staff members was essential to ensure that the rest of the camp wouldn’t become frantic and unfocused when the ambulances arrived. Looking at “strength” in this regard, I think that a lack of strength is somewhat of a disqualifying factor. I’d give a 1 in strength a 0.6 weighing factor and a 2 a 0.7. A 9 would be 0.95 and a 10 would be 1.0–the power of a strongly willed group member is invaluable. For the remaining values, the equation (0.025 * (strength - 2)) + 0.75 would be used.

Third is endurance, which in my opinion is the most important factor among these four. It’s crucial for a team member to work diligently even during times of wear, stress, long hours or adverse conditions. My most productive hours in this library have been spent in long hours on a second coffee. But it’s also important to maintain a good work-life balance to ensure employee quality of life. More than the other factors, I think this factor depends on the average needs of the group. If the average endurance among existing members is below 5, then the equation 0.5 + (endurance * 0.05) is used. If the average endurance is currently 5 or above, then 0.6 + (endurance * 0.04) is used.

Spice tolerance is certainly the odd one out, but I found some interesting research findings correlating spice tolerance with personality factors. In Personality factors predict spicy food liking and intake, authors Brynes and Hayes conclude that liking of spicy meals was strongly correlated with a sensation-seeking personality type, with an r-value of 0.50. The article can be read here: https://www.sciencedirect.com/science/article/pii/S0950329312001917. I imagine sensation seeking to be associated with an enhanced desire for unique experiences, or in other words, risk-taking. Risk-taking can be an issue in one’s personal life, say, if they lose their finances to online slot machines or if they decide to parkour atop the New York subway. But in a group project setting, it’s a huge benefit. Risk-taking leaders can drive innovation and introduce fresh ideas to a team. I also don’t think a lack of risk-taking is disqualifying, and there’s only so much benefit that more innovative ideas can add to a dynamic when there’s already so many ideas flying around. Too much and a group might not be able to move forward with one idea. If the average spice tolerance is below 5, I’ll give weighing factor of 0.75 + (0.025 * spicyFoodTolerance). If the average is between 5 and 8, inclusive, I’ll give a weighing factor of 0.9 + (0.01 * spicyFoodTolerance). If it’s above 8, I’ll give 1.0 - (0.01 * spicyFoodTolerance).

TLDR:

If intelligence >= 4, weigh it as 0.5 + (intelligence * 0.05)

If intelligence < 4,

    If there’s an existing member with intelligence < 4, weigh it as 0.4 + (intelligence * 0.1)

    Otherwise, weigh it as 0.65

For strength:

    If strength = 1, weigh it as 0.6

    If strength = 2, weigh it as 0.7

    If strength = 9, weigh it as 0.95

    If strength = 10, weigh it as 1.0

    Otherwise, use (0.025 * (strength - 2)) + 0.75

If average endurance < 5, weigh it as 0.5 + (endurance * 0.05)

If average endurance >= 5, weigh it as  0.6 + (endurance * 0.04)

If average spicyFoodTolerance < 5, weigh it as 0.75 + (0.025 * spicyFoodTolerance)

If average spicyFoodTolerance > 8, weigh it as 1.0 - (0.01 * spicyFoodTolerance)

Otherwise, weigh it as 0.9 + (0.01 * spicyFoodTolerance).

Multiply the 4 weighing factors together to get the final compatibility score.
