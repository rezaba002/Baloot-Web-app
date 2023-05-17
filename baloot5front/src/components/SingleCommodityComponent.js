import React from "react";
import axios from "axios";
import {Rating} from "@mui/material";

import AddToCartCard from "../cards/AddToCartCard";
import AddToCartCardInSingleCommodity from "../cards/AddToCartCardInSingleCommodity";
import LoginRegisterCard from "../cards/LoginRegisterCard";

const SINGLE_PROVIDER_API = "http://localhost:8080/provider";
const SINGLE_COMMODITY_API = "http://localhost:8080/commodity";
const COMMENTS_API = "http://localhost:8080/comments";
const SUGGESTED_API = "http://localhost:8080/suggested";
const RATE_API = "http://localhost:8080/rate";

class SingleCommodityComponent extends React.Component {
    constructor(props) {
        super(props);

        this.loadProvider = this.loadProvider.bind(this);
        this.loadCommodity = this.loadCommodity.bind(this);
        this.handleRating = this.handleRating.bind(this);
        this.scoreSubmit = this.scoreSubmit.bind(this);

        this.state = {
            currentUserNull: false,
            commodityId: "",
            score: 0
        }

    }

    loadProvider(data) {
        let path = "/providers/" + data;
        localStorage.setItem('idProvider', data);
        window.location.replace("http://localhost:3000" + path);
    }

    loadCommodity(commodityId, providerId) {
        axios.post(SINGLE_COMMODITY_API, commodityId)
            .then(response => {
                localStorage.setItem('commodity', JSON.stringify(response.data));
            })
        axios.post(SINGLE_PROVIDER_API, providerId)
            .then(response => {
                localStorage.setItem('commodityProviderName', response.data.name);
            })
        axios.post(COMMENTS_API, commodityId)
            .then(response => {
                localStorage.setItem("commodityComments", JSON.stringify(response.data));
            })
        axios.post(SUGGESTED_API, commodityId)
            .then(response => {
                localStorage.setItem("commoditySuggested", JSON.stringify(response.data));
            })

        let path = "/commodities/" + commodityId;

        setTimeout(() => {
            window.location.replace("http://localhost:3000" + path);
        }, 88);
    }

    handleRating(event) {
        this.setState({score: event.target.value});
    }

    scoreSubmit() {
        const commodityId = this.state.commodityId;
        const score = this.state.score;

        setTimeout(() => {
            const data = {"commodityId": commodityId, "score": score}

            axios.post(RATE_API, data)
                .then(response => {
                    localStorage.setItem('commodity', JSON.stringify(response.data));
                    window.location.replace("/commodities/" + commodityId);
                })
                .catch((error) => {
                    console.log(error)
                });
        },88)
    }

    componentDidMount() {
        if (localStorage.getItem('currentUsername') === 'null') {
            this.setState({currentUserNull:true});
        }
        this.setState({commodityId: JSON.parse(localStorage.getItem('commodity')).id});
    }

    render() {
        const currentUsername = localStorage.getItem('currentUsername');

        let providerName = localStorage.getItem('commodityProviderName');
        let commodity = JSON.parse(localStorage.getItem('commodity'));
        let comments = localStorage.getItem('commodityComments');
        let suggestedCommodities = JSON.parse(localStorage.getItem('commoditySuggested'));

        return (
            <div id="body-wrapper" className="container-fluid p-0 d-flex flex-column flex-nowrap">
                <header>
                    <div id="header-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-1 d-flex flex-row flex-nowrap justify-content-between align-items-center">
                        <div id="header-logo" className="d-flex flex-row flex-nowrap align-items-center">
                            <a href="/" id="baloot-logo"></a>
                            <a href="/" id="baloot-title"
                               className="color-copper ms-2 fw-semibold fs-4">Baloot</a>
                        </div>
                        <div>
                            {this.state.currentUserNull
                                ?
                                <LoginRegisterCard />
                                :
                                <div id="user-cart">
                                    <div className="btn-toolbar wd-100" role="toolbar">
                                        <a href="/user"
                                           className="btn color-brown col-5 d-flex justify-content-center align-items-center">{currentUsername}</a>
                                        <a href="/user"
                                           className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                            <span className="color-copper me-auto">Cart</span>
                                            <span className="color-copper">0</span>
                                        </a>
                                    </div>
                                </div>
                            }
                        </div>
                    </div>
                </header>
                <main>
                    <div id="main-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-0 mb-4 mb-xl-5 d-flex flex-column flex-nowrap align-items-center">

                                    <div id="commodity" className="main-item col-11 row g-0">
                                        <div id="commodity-image" className="col-6 px-2">
                                            <div>
                                                <img src={commodity.image} alt="commodity" className="col-12"/>
                                            </div>
                                        </div>
                                        <div id="commodity-details" className="col-6 px-2">
                                            <div id="commodity-name"
                                                 className="color-brown fw-bold">{commodity.name}</div>
                                            <div id="commodity-inStock"
                                                 className="color-red fw-semibold">{commodity.inStock} left in
                                                stock
                                            </div>
                                            <div id="commodity-provider" className="fw-semibold">
                                                by
                                                <a style={{cursor: 'pointer'}} onClick={() => this.loadProvider(commodity.providerId)}
                                                   className="color-blue"> {providerName}</a>
                                            </div>
                                            <div id="commodity-categories" className="color-brown">
                                                Category(s)
                                                <ul>
                                                    <li>{commodity.categories}</li>
                                                </ul>
                                            </div>
                                            <div id="price-add"
                                                 className="col-10 px-3 py-2 px-xl-5 py-xl-4 mb-4 mb-xl-5 d-flex flex-row flex-nowrap align-items-center">
                                                <span id="commodity-price"
                                                      className="color-brown fw-semibold me-auto">{commodity.price}T</span>
                                                <AddToCartCardInSingleCommodity commodity = {commodity} inStock={commodity.inStock}/>
                                            </div>
                                            <div id="commodity-rate"
                                                 className="col-10 ps-2 pe-3 ps-xl-4 pe-xl-5 card-text fw-semibold d-flex flex-row flex-nowrap align-items-center">
                                                <div id="score" className="d-flex flex-column flex-nowrap me-auto">
                                                    <Rating onChange={this.handleRating} value={this.state.score} name="customized-10" precision={0.1} defaultValue={0} max={10} />
                                                </div>
                                                <button id="sumbit" type="submit" onClick={this.scoreSubmit}
                                                        className="btn bg-brown color-linen py-2 rounded-3">submit
                                                </button>
                                            </div>
                                            <div id="commodity-rating">
                                                <span className="logo-star"></span>
                                                <span className="color-brown fs-3 fw-semibold">{commodity.rating}</span>
                                                <span className="color-grey fw-semibold">({commodity.numberOfVoters})</span>
                                            </div>
                                        </div>
                                    </div>
                        {/*{*/}
                        {/*    comments.map(*/}
                        {/*        comment =>*/}

                        {/*            <div id="comments" className="main-item col-11">*/}
                        {/*                <div id="comments-title" className="fs-4">*/}

                        {/*                    <span className="color-brown">Comments</span>*/}
                        {/*                    <span className="color-grey">(2)</span>*/}
                        {/*                </div>*/}
                        {/*                <div className="comment d-flex flex-column flex-nowrap justify-content-center">*/}
                        {/*                    <div className="comment-header">{comment.text}</div>*/}
                        {/*                    <div className="comment-date-user">*/}
                        {/*                        <span>{comment.date}</span>*/}
                        {/*                        <span>.</span>*/}
                        {/*                        <span>{comment.userEmail}</span>*/}
                        {/*                    </div>*/}
                        {/*                    <div className="comment-like-dislike align-self-end">*/}
                        {/*                        <span>Is this comment helpful?</span>*/}
                        {/*                        <span>*/}
                        {/*    {comment.like}*/}
                        {/*    <span className="logo logo-like"></span>*/}
                        {/*    </span>*/}
                        {/*                        <span>*/}
                        {/*    {comment.dislike}*/}
                        {/*    <span className="logo logo-dislike"></span>*/}
                        {/*    </span>*/}
                        {/*                    </div>*/}
                        {/*                </div>*/}

                        {/*                <div id="comment-submit">*/}
                        {/*                    <div className="color-brown fs-4">Submit your opinion</div>*/}
                        {/*                    <form action="#" className="mt-4 d-flex flex-row flex-nowrap">*/}
                        {/*    <textarea name="comment-text" id="comment-text" rows="4"*/}
                        {/*              className="col-10 bg-smoke px-4 py-3 fs-5 rounded-3"></textarea>*/}
                        {/*                        <button type="submit"*/}
                        {/*                                className="btn bg-brown color-linen px-4 py-2 ms-auto rounded-3 fs-5 align-self-end">Post*/}
                        {/*                        </button>*/}
                        {/*                    </form>*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*    )*/}
                        {/*}*/}

                        <div id="commodities" className="main-item col-11">
                            <div className="color-brown mt-4 mb-5 fs-3 fw-semibold">You also might like...</div>
                            <div className="row row-cols-4 g-4">
                                {
                                    suggestedCommodities.map(
                                        suggested =>
                                    <div className="col">
                                        <div className="card h-100 px-2 py-3">
                                            <div className="card-body py-0">
                                                <div className="card-title color-brown m-0 fw-semibold">
                                                    <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(suggested.id, suggested.providerId)}>
                                                        {suggested.name}
                                                    </div>
                                                </div>
                                                <div className="card-text color-red fw-semibold">{suggested.inStock} left in stock</div>
                                            </div>
                                            <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(suggested.id, suggested.providerId)}>
                                                <img src={suggested.image} className="card-img-top my-2" alt="commodity"/>
                                            </div>
                                            <div className="card-body py-0 d-flex flex-row flex-nowrap align-items-center">
                                                <span className="color-brown fw-semibold me-auto">{suggested.price}T</span>
                                                <AddToCartCard commodityId = {suggested.id} inStock={suggested.inStock}/>
                                            </div>
                                        </div>
                                    </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>
                </main>
                <footer>
                    <div id="footer-wrapper"
                         className="bg-brown color-white px-5 py-2 fw-semibold container-fluid d-flex flex-row flex-nowrap justify-content-center align-items-center">
                        <div className="color-linen fs-5">2023 © VPT</div>
                    </div>
                </footer>
            </div>
        )
    }
}

export default SingleCommodityComponent