package bloodcenter.api_key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KeyService {
    private final KeyRepository keyRepository;
    @Autowired
    public KeyService (KeyRepository keyRepository){
        this.keyRepository = keyRepository;
    }

    public String createKey(String email) throws Key.ApiKeyAlreadyExistsException {
        if(keyRepository.findByEmail(email) != null)
            throw new Key.ApiKeyAlreadyExistsException("Key for the given email already exists.");

        Key key = new Key();
        key.setEmail(email);
        key.setKey(generateRandomString());

        keyRepository.save(key);

        return key.getKey();
    }

    public boolean isKeyValid(String email, String apiKey){
        Key key = keyRepository.findByEmail(email);
        String[] arr = apiKey.split(" ");

        return arr.length == 2 && key != null && arr[1].equals(key.getKey());
    }


    private String generateRandomString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
