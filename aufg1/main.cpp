#include <iostream>
using namespace std;

double calculatePrice(double baseprice, double specialprice, double extraprice, int extras, double discount) {
    double addon_discount;
    double result;
    
    if (extras >= 3) 
        addon_discount = 10;
    else if (extras >= 5)
        addon_discount = 15;
    else 
        addon_discount = 0;
    
    if (discount > addon_discount)
        addon_discount = discount;
    
    result = baseprice/100.0 * (100-discount) + specialprice
            + extraprice/100.0 * (100-addon_discount);
    
    return result;
}


bool test_calculate_price() {
    bool test_ok = true;
    double price;

    // Test 1
    price = calculatePrice(100, 20, 50, 2, 0);   // No discounts apply
    if (price != 170) {
        cout << "Test 1 FAIL (got " << price << ")\n";
        test_ok = false;
    }

    // Test 2
    price = calculatePrice(100, 20, 50, 4, 0);   // 10% addon discount
    if (price != 165) {
        cout << "Test 2 FAIL (got " << price << ")\n";
        test_ok = false;
    }

    // Test 3
    price = calculatePrice(100, 20, 50, 6, 20);  // 20% global discount beats addon
    if (price != 150) {
        cout << "Test 3 FAIL (got " << price << ")\n";
        test_ok = false;
    }

    if (test_ok)
        cout << "All tests OK!\n";

    return test_ok;
}

int main() {
    test_calculate_price();
    return 0;
}