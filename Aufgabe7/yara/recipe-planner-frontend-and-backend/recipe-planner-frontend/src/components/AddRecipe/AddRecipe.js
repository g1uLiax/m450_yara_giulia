import React, {useState} from 'react';
import './AddRecipe.css';
import {Form, Button, Col, Row} from 'react-bootstrap';
import { useNavigate } from "react-router-dom";

import axios from "axios";
import AddIngredient from "../AddIngredient/AddIngredient";

function AddRecipe() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        "name": '',
        "description": '',
        "imageUrl": '',
        "ingredients": [],
        "id": null
    })

    const [listId, setListId] = useState(1)

    const addIngredient = () => {

        setFormData(({...formData, ingredients: [
                ...formData.ingredients, {
                    listId: listId,
                    name: '',
                    unit: 'PIECE',
                    amount: ''
                }
            ]}))
        setListId(listId + 1)

    }
    const updateIngredient = (ingredientObj) => {
        const updatedIngredients = formData.ingredients.map((ingredient) => {
            if (ingredient.listId === ingredientObj.listId) {
                return ingredientObj
            }
            return ingredient
        })
        setFormData({...formData, ingredients: updatedIngredients})
    }

    const removeIngredient = (ingredientObj) => {
        const updatedIngredients = formData.ingredients.filter((ingredient) => ingredient.listId !== ingredientObj.listId)
        setFormData({...formData, ingredients: updatedIngredients})
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Clean up listId before sending to backend if necessary
            const dataToSubmit = {
                ...formData,
                ingredients: formData.ingredients.map(({listId, ...rest}) => rest)
            };
            await axios.post("http://localhost:8080/api/recipes", dataToSubmit);
            navigate("/");
        } catch (error) {
            console.error("Error adding recipe", error);
        }
    };

    const renderIngredients = formData.ingredients.map(ingredient => <AddIngredient
        key={ingredient.listId}
        ingredient={ingredient}
        updateIngredient={updateIngredient}
        removeIngredient={removeIngredient}
    />)

    return (
        <>
            <div className="bg">
                <div className="m-3">
                    <h1 className="h3 bg-dark text-bg-primary mt-2">Add Recipe</h1>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-1" controlId="formBasicName">
                            <Form.Label>Recipe Name:</Form.Label>
                            <Form.Control 
                                name="name" 
                                value={formData.name} 
                                onChange={handleChange} 
                                placeholder="Name"
                            />
                        </Form.Group>
                        <Form.Group className="mb-1" controlId="formBasicDescription">
                            <Form.Label>Description:</Form.Label>
                            <Form.Control 
                                name="description" 
                                value={formData.description} 
                                onChange={handleChange} 
                                placeholder="Description"
                            />
                        </Form.Group>
                        <Form.Group className="mb-1 mb-5" controlId="formBasicImageUrl">
                            <Form.Label>Image URL:</Form.Label>
                            <Form.Control 
                                name="imageUrl" 
                                value={formData.imageUrl} 
                                onChange={handleChange} 
                                placeholder="URL"
                            />
                        </Form.Group>
                        <Row>
                            <Col>Ingredient</Col>
                            <Col>Unit</Col>
                            <Col>Quanity</Col>
                            <Col xs={1}></Col>
                        </Row>
                        <hr/>
                        <Row>
                            <br></br>
                        </Row>
                        {renderIngredients}
                        <Row>
                            <br></br>
                            <Button
                                variant='warning'
                                onClick={addIngredient}
                                className="mt-1"
                                >Add Ingredient</Button>
                        </Row>
                        <Button variant="primary"  type="submit" className="mb-5 mt-3">
                            Submit
                        </Button>
                    </Form>
                </div>

            </div>

        </>
    )
}

export default AddRecipe;
