import React, {useEffect, useState} from 'react';
import './EditRecipe.css';
import {Form, Button, Col, Row} from 'react-bootstrap';
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import AddIngredient from "../AddIngredient/AddIngredient";

function EditRecipe() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        "name": '',
        "description": '',
        "imageUrl": '',
        "ingredients": [],
        "id": null
    })

    const [listId, setListId] = useState(1)

    useEffect(() => {
        axios.get(`http://localhost:8080/api/recipes/recipe/${id}`).then((response) => {
            const recipe = response.data;
            // Add listId to ingredients for UI management if they don't have it
            const ingredientsWithListId = recipe.ingredients.map((ing, index) => ({
                ...ing,
                listId: index + 1
            }));
            setFormData({
                ...recipe,
                ingredients: ingredientsWithListId
            });
            setListId(ingredientsWithListId.length + 1);
        });
    }, [id]);

    const addIngredient = () => {
        setFormData(({...formData, ingredients: [
                ...formData.ingredients, {
                    listId: listId,
                    ingredient: '',
                    unit: 'PIECE',
                    quantity: ''
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

    const submitEdit = () => {
        // Remove listId before sending to backend
        const ingredientsToSubmit = formData.ingredients.map(({listId, ...rest}) => rest);
        const dataToSubmit = {...formData, ingredients: ingredientsToSubmit};
        
        axios.put("http://localhost:8080/api/recipes", dataToSubmit).then((response) => {
            console.log(response);
            navigate("/");
        });
    }

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
                    <h1 className="h3 bg-dark text-bg-primary mt-2">Edit Recipe</h1>
                    <Form.Group className="mb-1" controlId="formBasicName">
                        <Form.Label>Recipe Name:</Form.Label>
                        <Form.Control 
                            value={formData.name}
                            onChange={(e) => setFormData({...formData, name: e.target.value})}
                            placeholder="Name"/>
                    </Form.Group>
                    <Form.Group className="mb-1" controlId="formBasicDescription">
                        <Form.Label>Description:</Form.Label>
                        <Form.Control 
                            value={formData.description}
                            onChange={(e) => setFormData({...formData, description: e.target.value})}
                            placeholder="Description"/>
                    </Form.Group>
                    <Form.Group className="mb-1 mb-5" controlId="formBasicImageUrl">
                        <Form.Label>Image URL:</Form.Label>
                        <Form.Control 
                            value={formData.imageUrl}
                            onChange={(e) => setFormData({...formData, imageUrl: e.target.value})}
                            placeholder="URL"/>
                    </Form.Group>
                    <Row>
                        <Col>Ingredient</Col>
                        <Col>Unit</Col>
                        <Col>Quantity</Col>
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
                    <Button variant="primary" onClick={submitEdit} className="mb-5">
                        Save Changes
                    </Button>
                </div>
            </div>
        </>
    )
}

export default EditRecipe;
