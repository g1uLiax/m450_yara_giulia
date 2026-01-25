import React, {useState} from 'react';
import './AddRecipe.css';
import {Form, Button, Col, Row, Alert} from 'react-bootstrap';
import confetti from 'canvas-confetti';

import axios from "axios";
import AddIngredient from "../AddIngredient/AddIngredient";

function AddRecipe() {
    const [formData, setFormData] = useState({
        "name": '',
        "description": '',
        "imageUrl": '',
        "ingredients": [],
    })

    const [status, setStatus] = useState({ type: '', message: '' });

    const [listId, setListId] = useState(1)

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const addIngredient = () => {

        setFormData(({...formData, ingredients: [
                ...formData.ingredients, {
                    listId: listId,
                    name: '',
                    unit: 'PIECE',
                    amount: 0
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

    const onSubmit = async (e) => {
        e.preventDefault();
        setStatus({ type: 'info', message: 'Saving recipe...' });

        try {
            // Clean up listId before sending to backend
            const cleanedIngredients = formData.ingredients.map(({ listId, ...rest }) => rest);
            const dataToSubmit = { ...formData, ingredients: cleanedIngredients };

            const response = await axios.post('http://localhost:8080/api/recipes', dataToSubmit);
            
            if (response.status === 201) {
                setStatus({ type: 'success', message: 'Recipe added successfully!' });
                
                // The interesting part: Confetti!
                confetti({
                    particleCount: 150,
                    spread: 70,
                    origin: { y: 0.6 }
                });

                // Reset form
                setFormData({
                    "name": '',
                    "description": '',
                    "imageUrl": '',
                    "ingredients": [],
                });
                setListId(1);
            }
        } catch (error) {
            console.error("Error adding recipe:", error);
            setStatus({ type: 'danger', message: 'Failed to add recipe. Is the backend running?' });
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
            <div className="bg pb-5">
                <div className="container m-3">
                    <h1 className="h3 bg-dark text-white p-3 rounded mt-2">✨ Add a New Culinary Masterpiece ✨</h1>
                    
                    {status.message && (
                        <Alert variant={status.type} onClose={() => setStatus({type:'', message:''})} dismissible>
                            {status.message}
                        </Alert>
                    )}

                    <Form onSubmit={onSubmit}>
                        <Form.Group className="mb-3" controlId="formBasicName">
                            <Form.Label className="fw-bold">Recipe Name:</Form.Label>
                            <Form.Control 
                                name="name"
                                value={formData.name}
                                onChange={handleInputChange}
                                placeholder="e.g. Grandma's Famous Lasagna" 
                                required
                            />
                        </Form.Group>
                        
                        <Form.Group className="mb-3" controlId="formBasicDescription">
                            <Form.Label className="fw-bold">Description:</Form.Label>
                            <Form.Control 
                                as="textarea"
                                rows={3}
                                name="description"
                                value={formData.description}
                                onChange={handleInputChange}
                                placeholder="Describe the magic..." 
                                required
                            />
                        </Form.Group>
                        
                        <Form.Group className="mb-4" controlId="formBasicImageUrl">
                            <Form.Label className="fw-bold">Image URL:</Form.Label>
                            <Form.Control 
                                name="imageUrl"
                                value={formData.imageUrl}
                                onChange={handleInputChange}
                                placeholder="http://..." 
                            />
                        </Form.Group>

                        <h4 className="mt-4 mb-3">Ingredients</h4>
                        <Row className="fw-bold mb-2">
                            <Col>Ingredient</Col>
                            <Col>Unit</Col>
                            <Col>Amount</Col>
                            <Col xs={1}></Col>
                        </Row>
                        <hr/>
                        
                        {renderIngredients}
                        
                        <Row className="mt-3 mb-4">
                            <Col>
                                <Button
                                    variant='outline-primary'
                                    onClick={addIngredient}
                                    className="w-100"
                                >+ Add Ingredient</Button>
                            </Col>
                        </Row>

                        <div className="d-grid gap-2">
                            <Button variant="success" size="lg" type="submit">
                                Create Recipe! 🚀
                            </Button>
                        </div>
                    </Form>
                </div>
            </div>
        </>
    )
}

export default AddRecipe;
