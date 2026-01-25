import React, {useState} from 'react';
import './AddRecipe.css';
import { useForm } from "react-hook-form";
import {Form, Button, Col, Row} from 'react-bootstrap';

import axios from "axios";
import AddIngredient from "../AddIngredient/AddIngredient";

function AddRecipe() {
    const [ ingredients, setIngredients ] = useState([])
    const [formData, setFormData] = useState({
        "name": '',
        "description": '',
        "imageUrl": '',
        "ingredients": [],
        "id": null
    })

    const [listId, setListId] = useState(1)

    const {
        register,
        handleSubmit,
        watch,
        formState: {errors},
    } = useForm()

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

    const renderIngredients = formData.ingredients.map(ingredient => <AddIngredient
        key={ingredient.listId}
        ingredient={ingredient}
        ingredients={ingredients}
        listId={listId - 1}
        updateIngredient={updateIngredient}
        removeIngredient={removeIngredient}
    />)

    const addRecipe = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/recipes", formData);
            console.log("Recipe added successfully:", response.data);
            // Optionally redirect or show success message
        } catch (error) {
            console.error("Error adding recipe:", error);
        }
    }

    const handleInputChange = (e) => {
        const { id, value } = e.target;
        setFormData({ ...formData, [id]: value });
    }

    return (
        <>
            <div className="bg">
                <div className="m-3">
                    <h1 className="h3 bg-dark text-bg-primary mt-2">Add Recipe</h1>
                    <Form.Group className="mb-1" controlId="name">
                        <Form.Label>Recipe Name:</Form.Label>
                        <Form.Control placeholder="Name" value={formData.name} onChange={handleInputChange}/>
                    </Form.Group><Form.Group className="mb-1" controlId="description">
                        <Form.Label>Description:</Form.Label>
                        <Form.Control placeholder="Description" value={formData.description} onChange={handleInputChange}/>
                    </Form.Group><Form.Group className="mb-1 mb-5" controlId="imageUrl">
                        <Form.Label>Image URL:</Form.Label>
                        <Form.Control placeholder="URL" value={formData.imageUrl} onChange={handleInputChange}/>
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
                    <Button variant="primary" onClick={addRecipe} className="mb-5">
                        Submit
                    </Button>
                </div>

            </div>

        </>
    )
}

export default AddRecipe;
