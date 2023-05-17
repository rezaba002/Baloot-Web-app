import axios from "axios";

const DISCOUNT_API = "http://localhost:8080/discounts";

class DiscountService {
    getDiscounts() {
        return axios.get(DISCOUNT_API);
    }
}
export default new DiscountService();