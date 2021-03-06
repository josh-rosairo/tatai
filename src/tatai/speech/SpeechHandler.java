package tatai.speech;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
** Handles all speech and bash parts of speech recognition.
** @author jtha772, dli294
**/
public class SpeechHandler {

	// English translations for each Maori Number from 1 to 10.
	private static List<String> _maoriNumTranslations = Arrays.asList("tahi","rua", "toru", "whaa", "rima", "ono", "whitu", "waru", "iwa", "tekau");

	// Filename base.
	final static private String FILENAME = "recording.wav";
	
    /**
    ** Constructor.
    **/
	public SpeechHandler() {
		
	}
	
    /**
    ** Executes some bash command received as an argument.
    ** @arg String cmd The bash command to execute.
    ** @return List<String> The output after running the command, as a list of strings.
    ** @author dli294
    **/
	private List<String> executeCommand(String cmd) {
		// Read output from the bash.
		List<String> output = new ArrayList<>();
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
            // Individual lines.
			String line = null;
			while ((line = stdoutBuffered.readLine()) != null ) {
				output.add(line);
			}
		} catch (Exception e) {
			
		}
        // Return output.
		return output;
	}

    /**
    ** Records and processes the user audio.
    ** @author jtha772
    **/
	public void recordAndProcess() {
		// Remove any previous recording files so overwrite prompt doesn't hold up thread.
		executeCommand("rm "+FILENAME);
		// Record the user with ffmpeg.
		executeCommand("ffmpeg -f alsa -ar 22050 -i default -loglevel quiet -t 3 "+FILENAME);
		// Run HVite and write output to file.
		executeCommand("cp " + FILENAME + " /home/se206/Documents/HTK/MaoriNumbers/");
		executeCommand("cd /home/se206/Documents/HTK/MaoriNumbers/; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList " + FILENAME);
		executeCommand("cd -");
	}
	
	/**
	 * Plays the audio file.
	 * @author jtha772
	 */
	public void play() {
		executeCommand("aplay "+FILENAME);
	}

    /**
    ** Determines if the recording is the correct Maori word.
    ** @arg int numToSay The number to match the recording to.
    ** @return boolean True if the recording matches the number, false otherwise.
    ** @author jtha772
    **/
	public boolean isRecordingCorrect(int numToSay) {
		
    	// Get HVite speech recognition output as a list of strings.
    	List<String> maoriRecogOutput = executeCommand("cd /home/se206/Documents/HTK/MaoriNumbers/; cat recout.mlf");
    	executeCommand("cd -");
    	Iterator<String> it = maoriRecogOutput.iterator();
    	
    	
    	String result = "";
    	// Iterate through the output strings, determining if recording matches correct Maori pronunciation of the number.
    	while(it.hasNext()){
    		
    		try {
    			// Get pronunciation output between silences.
    			String str = it.next();
	        	result = str;
        		while (!(str = it.next()).equals("sil")) {
        			result = result + " " + str;
        		}
        	}
        	catch (NoSuchElementException e) {
        		break;
        	}
        	
    		if (result.equals(ConvertIntToMaori(numToSay))) {
    			return true;
    		}
    		
  		}
    	
    	return false;
	}

	/**
    ** Converts the number to be said to a string containing the correct Maori pronunciation
    ** @arg int numToSay The number to match the recording to.
    ** @return String of correct Maori pronunciation of number
    ** @author jtha772
    **/
	public static String ConvertIntToMaori(int numToSay) {
		String maoriAnswer;
		String prefix;
		String suffix;
		
		// Converts a number to Maori.

		if (numToSay <= 10) {
			maoriAnswer = _maoriNumTranslations.get(numToSay-1);
		}
		else if (numToSay%10 == 0) {
			prefix = _maoriNumTranslations.get((numToSay/10)-1);
			maoriAnswer = prefix + " tekau";
		}
		else {
			if (numToSay/10 == 1) {
				prefix = "";
			} else {
				prefix = _maoriNumTranslations.get((numToSay/10)-1) + " ";
			}
			suffix = _maoriNumTranslations.get((numToSay%10)-1);
			maoriAnswer = prefix + "tekau maa " + suffix;
		}
		
		return maoriAnswer;
	}
}
