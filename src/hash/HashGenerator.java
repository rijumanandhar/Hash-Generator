package hash;

import java.io.File;

public interface HashGenerator {
	
	String produceFileHash(File file);

	String produceDirHash(File[] folder);

	String produceDirMetaHash(File[] folder);


}
