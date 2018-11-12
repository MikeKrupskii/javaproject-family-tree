package familyTree.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import familyTree.model.Member;

public class SerializationService {

	/**
	 * Class needed to serialize the TreeView into a JSON object
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */

	/**
	 * 
	 * @param member
	 *            Member to serialize into JSON object
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void serialize(Member member) throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper map = new ObjectMapper();
		String serializedString = map.writeValueAsString(member);
		File file = new File("json.txt");
		file.createNewFile();
		FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(serializedString);
		fileWriter.flush();
		fileWriter.close();
		System.out.println(serializedString);
	}

}
