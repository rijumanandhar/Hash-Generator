package hash;

import java.io.File;

public class AlgorithmThree implements HashGenerator {

	@Override
	public String produceFileHash(File file) {
		// TODO Auto-generated method stub
		return "This is algorithm three. folder hash";
	}

	@Override
	public String produceDirHash(File[] folder) {
		// TODO Auto-generated method stub
		return "This is algorithm three. folder hash";
	}

	@Override
	public String produceDirMetaHash(File[] folder) {
		// TODO Auto-generated method stub
		return "This is algorithm three. folder meta hash";
	}

}
