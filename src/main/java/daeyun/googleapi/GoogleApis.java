package daeyun.googleapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

@Component
public class GoogleApis {
    
    private static String PACKGENAME = "";
    private static String KEYFILE = "/googleapi_key.properties";
        
    private static GoogleCredentials getCredentials() {
        FileInputStream credentialsJSON = null;
              
        try {
            File file = new File(GoogleApis.class.getClass().getResource(KEYFILE).getFile());
            credentialsJSON = new FileInputStream(file.getPath());    
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsJSON).createScoped(AndroidPublisherScopes.ANDROIDPUBLISHER);
            AccessToken accessToken = credentials.refreshAccessToken();
            return credentials;
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
    
    public static SubscriptionPurchase checkSubscription(String productId, String token) {
        GoogleCredentials credentials = getCredentials();
        if(credentials == null) {
            return null;
        }
        try {
            AndroidPublisher pub = new AndroidPublisher.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials)
            ).setApplicationName(PACKGENAME).build();
            
            SubscriptionPurchase purchase = pub.purchases().subscriptions().get(PACKGENAME, productId, token).execute();
            /**
             * purchase.getPaymentState() == 1 // 정상 
             */
            return purchase;
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static ProductPurchase checkProduct(String productId, String token) {
        GoogleCredentials credentials = getCredentials();
        if(credentials == null) {
            return null;
        }
        try {
            AndroidPublisher pub = new AndroidPublisher.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials)
            ).setApplicationName(PACKGENAME).build();
            
            ProductPurchase purchase = pub.purchases().products().get(PACKGENAME, productId, token).execute();
            /**
             * purchase.getPurchaseState() == 0 // 정상 
             */
            return purchase;
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
