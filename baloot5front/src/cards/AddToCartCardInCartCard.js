import React from "react";
import axios from "axios";

const CART_API = "http://localhost:8080/cart";

class AddToCartCardInCartCard extends React.Component {
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
    }

    componentDidMount() {
        this.setState({commodity: this.props.commodity});
        this.setState({quantity: this.props.quantity});
    }

    render() {
        return (
            <div>
                <button className="col-3 h-100 d-flex flex-column justify-content-center" onClick={this.decreaseQuantity}>-
                </button>
                <input type="number" name="number" className="col-3 h-100 d-flex flex-column justify-content-center" value={this.state.quantity} readOnly/>
                <button className="col-3 h-100 d-flex flex-column justify-content-center" onClick={this.increaseQuantity}>+
                </button>
            </div>
        )
    }
}

export default AddToCartCardInCartCard