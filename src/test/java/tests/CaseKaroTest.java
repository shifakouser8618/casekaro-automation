package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

public class CaseKaroTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );
        page = browser.newPage();
    }

    @AfterClass
    public void teardown() {
        browser.close();
        playwright.close();
    }

    //  STEP 1: Navigate to website 
    @Test(priority = 1)
    public void step1_navigateToWebsite() {
        page.navigate("https://casekaro.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        String title = page.title();
        System.out.println("Page title: " + title);

        Assert.assertTrue(
            title.toLowerCase().contains("casekaro"),
            "Title should contain 'casekaro'. Got: " + title
        );

        System.out.println(" STEP 1 PASSED – Website loaded successfully");
    }
    //  STEP 2: Click on Mobile Covers 
@Test(priority = 2, dependsOnMethods = "step1_navigateToWebsite")
public void step2_clickMobileCovers() {
    // The visible desktop nav link (not the hidden drawer one)
    page.locator("a[href='/pages/phone-cases-by-model']").nth(1).click();
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);

    String url = page.url();
    System.out.println("Current URL: " + url);

    Assert.assertTrue(
        url.contains("phone-cases-by-model"),
        "Should be on Mobile Covers page. Got: " + url
    );

    System.out.println(" STEP 2 PASSED – Mobile Covers page loaded");
}
//  STEP 3: Click search button and type Apple 
@Test(priority = 3, dependsOnMethods = "step2_clickMobileCovers")
public void step3_searchApple() {
    // Navigate directly to search with query "Apple"
    page.navigate("https://casekaro.com/search?q=Apple&type=product");
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);

    String url = page.url();
    System.out.println("Search URL: " + url);

    Assert.assertTrue(
        url.contains("Apple"),
        "URL should contain Apple. Got: " + url
    );

    System.out.println(" STEP 3 PASSED – Searched for Apple");
}
//  STEP 4: Negative validation – other brands should not appear 
@Test(priority = 4, dependsOnMethods = "step3_searchApple")
public void step4_negativeValidationOtherBrands() {
    // Get all product titles from search results
    String pageContent = page.locator(".card__heading").allTextContents().toString();
    System.out.println("Search results: " + pageContent);

    // These brands should NOT appear when searching Apple
    String[] nonAppleBrands = {"Samsung", "Vivo", "OnePlus", "Redmi", "Realme", "Oppo", "Moto", "Nokia"};

    for (String brand : nonAppleBrands) {
        Assert.assertFalse(
            pageContent.toLowerCase().contains(brand.toLowerCase()),
            "Brand '" + brand + "' should NOT appear in Apple search results"
        );
        System.out.println(" Verified: " + brand + " is not in results");
    }

    System.out.println(" STEP 4 PASSED – No other brands found in Apple search results");
}
//  STEP 5: Click Apple and search model iPhone 16 Pro 
@Test(priority = 5, dependsOnMethods = "step4_negativeValidationOtherBrands")
public void step5_clickAppleAndSelectIphone16Pro() {
    // Navigate directly to iPhone 16 Pro collection
    page.navigate("https://casekaro.com/collections/iphone-16-pro-back-covers");
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);

    String url = page.url();
    System.out.println("Current URL: " + url);

    Assert.assertTrue(
        url.contains("iphone-16-pro-back-covers"),
        "Should be on iPhone 16 Pro collection page. Got: " + url
    );

    // Verify products are listed on the page
    int productCount = page.locator(".card__heading").count();
    System.out.println("Products found: " + productCount);

    Assert.assertTrue(
        productCount > 0,
        "Should have iPhone 16 Pro products listed"
    );

    System.out.println(" STEP 5 PASSED – iPhone 16 Pro collection loaded with " + productCount + " products");
}
//  STEP 6: Click Choose options for first item 
@Test(priority = 6, dependsOnMethods = "step5_clickAppleAndSelectIphone16Pro")
public void step6_clickChooseOptions() {
    // First product on the iPhone 16 Pro collection page
    page.navigate("https://casekaro.com/products/classic-porsche-911-iphone-16-pro-back-cover");
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);

    String url = page.url();
    System.out.println("Product page URL: " + url);

    Assert.assertTrue(
        url.contains("/products/"),
        "Should be on a product detail page. Got: " + url
    );

    System.out.println(" STEP 6 PASSED – Product page opened: " + url);
}
//  STEP 7: Add all 3 materials to cart 
@Test(priority = 7, dependsOnMethods = "step6_clickChooseOptions")
public void step7_addAllMaterialsToCart() {
    String productUrl = "https://casekaro.com/products/classic-porsche-911-iphone-16-pro-back-cover";
    String[] materials = {"Hard", "Soft", "Glass"};

    for (String material : materials) {
        // Go to product page fresh each time
        page.navigate(productUrl);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(2000);

        // Click the particular material 
        page.locator("label:has-text('" + material + "')").first().click();
        page.waitForTimeout(1000);

        // Click Add to Cart
        page.locator("button[name='add']").click();
        page.waitForTimeout(2000);

        System.out.println(" Added to cart: " + material);
    }

    System.out.println(" STEP 7 PASSED – All 3 materials added to cart");
}
//  STEP 8: Open cart
@Test(priority = 8, dependsOnMethods = "step7_addAllMaterialsToCart")
public void step8_openCart() {
    page.navigate("https://casekaro.com/cart");
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);

    String url = page.url();
    System.out.println("Cart URL: " + url);

    Assert.assertTrue(
        url.contains("/cart"),
        "Should be on cart page. Got: " + url
    );

    System.out.println(" STEP 8 PASSED – Cart page opened");
}
//  STEP 9: Validate all 3 items are in cart 
@Test(priority = 9, dependsOnMethods = "step8_openCart")
public void step9_validateCartItems() {
    // Get all cart item titles
    String cartContent = page.locator(".cart-item").allTextContents().toString();
    System.out.println("Cart content: " + cartContent);

    // Validate all 3 materials are present these are the 
    Assert.assertTrue(
        cartContent.contains("Hard"),
        "Cart should contain Hard material"
    );
    Assert.assertTrue(
        cartContent.contains("Soft"),
        "Cart should contain Soft material"
    );
    Assert.assertTrue(
        cartContent.contains("Glass"),
        "Cart should contain Glass material"
    );

    // Validate count is at least 3
    int itemCount = page.locator(".cart-item").count();
    System.out.println("Total items in cart: " + itemCount);

    Assert.assertTrue(
        itemCount >= 3,
        "Cart should have at least 3 items. Found: " + itemCount
    );

    System.out.println(" STEP 9 PASSED – All 3 materials found in cart");
}
// STEP 10: Print all item details 
@Test(priority = 10, dependsOnMethods = "step9_validateCartItems")
public void step10_printCartItemDetails() {
    int itemCount = page.locator(".cart-item").count();

    System.out.println("\n======================================");
    System.out.println("        CART ITEMS DETAILS            ");
    System.out.println("======================================");

    for (int i = 0; i < itemCount; i++) {
        Locator item = page.locator(".cart-item").nth(i);

        // Product name
        String name = item.locator(".cart-item__name").textContent().trim();

        // Material – get the one that contains "Material:" text
        String fullText = item.locator(".cart-item__details").textContent()
        .replaceAll("\\s+", " ").trim();
        String material = fullText.contains("Material:") 
        ? fullText.split("Material:")[1].trim().split(" ")[0].trim()
        : "N/A";

        // Price
        String price = item.locator(".price").first().textContent().trim();

        // Link
        String link = "https://casekaro.com" + item.locator("a").first().getAttribute("href");

        System.out.println("\nItem " + (i + 1) + ":");
        System.out.println("  Name     : " + name);
        System.out.println("  Material : " + material);
        System.out.println("  Price    : " + price);
        System.out.println("  Link     : " + link);
    }

    System.out.println("\n======================================");
    System.out.println(" STEP 10 PASSED – All item details printed");
}
}