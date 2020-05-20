package daeyun.googleapi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;

@SpringBootApplication
public class JavaGoogleapiApplication {
    
	public static void main(String[] args)  {
		
				 
		/**
		 * 구독(정기결제)
		 */
	    String productId1  = ""; //상품(제품)ID 
        String token1 = ""; // 영수증 정보 
		SubscriptionPurchase pur1 = GoogleApis.checkSubscription(productId1, token1);
		System.out.println(ToStringBuilder.reflectionToString(pur1));
		System.out.println(pur1.getOrderId());
        System.out.println(pur1.getPaymentState());
		/**
		 * 비소모성 
		 */
		String productId2  = ""; //상품(제품)ID 
        String token2 = ""; // 영수증 정보
		ProductPurchase pur2 = GoogleApis.checkProduct(productId2, token2);
	    System.out.println(pur2.getOrderId());
	    System.out.println(pur2.getPurchaseState());
		
		
	}

}
