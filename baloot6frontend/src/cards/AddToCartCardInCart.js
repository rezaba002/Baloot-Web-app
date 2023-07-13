import React from "react";
import axios from "axios";

const CART_API = "http://localhost:8080/cart";

class AddToCartCardInCart extends React.Component {
    constructor(props) {
        super(props);

        this.decreaseQuantity = this.decreaseQuantity.bind(this);
        this.increaseQuantity = this.increaseQuantity.bind(this);
        this.addToCart = this.addToCart.bind(this);

        this.state = {
            quantity: 0,
            buyType: "",
            commodity: "",
        }
    }

    increaseQuantity = () => {
        if (this.state.quantity < this.state.commodity.inStock) {
            this.setState({quantity: this.state.quantity + 1})
            this.setState({buyType: "increase"});
            this.addToCart();
        }
    }

    decreaseQuantity = () => {
        this.setState({quantity: this.state.quantity - 1})
        this.setState({buyType: "decrease"});
        this.addToCart();
    }

    addToCart(){
        setTimeout(() => {
            const currentUsername = localStorage.getItem("currentUsername");
            const commodityId = this.state.commodity.id;
            const buyType = this.state.buyType;
            const data = {"username": currentUsername, "commodityId": commodityId, "buyType": buyType};
            axios.post(CART_API, data)
                .then(response => {
                    if (this.state.quantity === 0)
                        window.location.replace("http://localhost:3000/user")
                    console.log(response)
                })
                .catch((error) => {
                    console.log(error)
                });
            }
        , 88)
        setTimeout(() => {
            this.props.handlePriceSum();
        },88)
    }

    componentDidMount() {
        this.setState({commodity: this.props.commodity});
        this.setState({quantity: this.props.commodity.quantity});
    }

    render() {
        return (
            <div className="col-2 d-flex flex-row flex-nowrap justify-content-center p-0">
                <div className="cart-quantity col-8 d-flex flex-row flex-nowrap justify-content-evenly rounded-3">
                    <div className=" col-3 h-100 d-flex flex-column justify-content-center align-items-center" onClick={this.decreaseQuantity}>-
                    </div>
                    <div className=" col-3 h-100 d-flex flex-column justify-content-center align-items-center">{this.state.quantity}
                    </div>
                    <div className=" col-3 h-100 d-flex flex-column justify-content-center align-items-center" onClick={this.increaseQuantity}>+
                    </div>
                </div>
            </div>
        )
    }
}

export default AddToCartCardInCart