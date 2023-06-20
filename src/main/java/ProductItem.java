//import com.techelevator.view.Vendable;
//
//import java.math.BigDecimal;
//
//public class ProductItem {
//        private String productID;
//        private String name;
//        private BigDecimal price;
//        private Vendable type;
//        private int quantitySold;
//        private int currentStock;
//        private static final int DEFAULT_QUANTITY = 5;
//
//        public ProductItem(String productID, String name, BigDecimal price, Vendable type) {
//            this.productID = productID;
//            this.name = name;
//            this.price = price;
//            this.type = type;
//            currentStock = DEFAULT_QUANTITY;
//            quantitySold = 0;
//        }
//
//        public String getProductID() {
//            return productID;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public BigDecimal getPrice() {
//            return price;
//        }
//
//
//        public void setQuantitySold(int quantitySold) {
//            this.quantitySold = quantitySold;
//        }
//
//        public int getQuantitySold() {
//            return quantitySold;
//        }
//
//        public int getCurrentStock() {
//            return currentStock;
//        }
//
//        public String vend(){
//            currentStock -=1;
//            return type.sound();
//        }
//    }
//
//
