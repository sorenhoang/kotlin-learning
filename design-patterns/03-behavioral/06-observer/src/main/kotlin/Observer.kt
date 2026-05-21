package behavioral.observer

// ========================================================
// 1. THE OBSERVER INTERFACE (The Subscriber)
// ========================================================
interface StockObserver {
    fun update(stockName: String, price: Double)
}

// ========================================================
// 2. THE SUBJECT INTERFACE (The Publisher)
// ========================================================
interface StockSubject {
    fun registerObserver(observer: StockObserver)
    fun removeObserver(observer: StockObserver)
    fun notifyObservers()
}

// ========================================================
// 3. THE CONCRETE SUBJECT
// ========================================================
class StockMarket : StockSubject {
    private val observers = mutableListOf<StockObserver>()
    private var stockName: String = ""
    private var price: Double = 0.0

    override fun registerObserver(observer: StockObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: StockObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        for (observer in observers) {
            observer.update(stockName, price)
        }
    }

    // Business logic: When price updates, trigger notifications immediately
    fun updatePrice(name: String, newPrice: Double) {
        println("\n📈 [StockMarket] Price changed for $name to \$$newPrice")
        this.stockName = name
        this.price = newPrice
        notifyObservers() // Push notification to all subscribers
    }
}

// ========================================================
// 4. THE CONCRETE OBSERVERS (The Subscribers)
// ========================================================

class MobileApp(private val userName: String) : StockObserver {
    override fun update(stockName: String, price: Double) {
        println("📱 [Mobile App - $userName]: Stock '$stockName' updated to \$$price")
    }
}

class DisplayBoard : StockObserver {
    override fun update(stockName: String, price: Double) {
        println("🖥️  [Big Display Board]: Live ticker -> $stockName is trading at \$$price")
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // 1. Create the central Stock Market (Publisher)
    val stockMarket = StockMarket()

    // 2. Create the Subscribers (Observers)
    val phoneAppOfAlex = MobileApp("Alex")
    val phoneAppOfJohn = MobileApp("John")
    val centralBoard = DisplayBoard()

    println("=== STEP 1: REGISTERING SUBSCRIBERS ===")
    stockMarket.registerObserver(phoneAppOfAlex)
    stockMarket.registerObserver(phoneAppOfJohn)
    stockMarket.registerObserver(centralBoard)

    // 3. First price change -> All 3 observers get notified
    stockMarket.updatePrice("AAPL (Apple)", 175.50)

    println("\n=== STEP 2: JOHN UNSUBSCRIBES ===")
    stockMarket.removeObserver(phoneAppOfJohn)

    // 4. Second price change -> Only Alex and Big Board get notified
    stockMarket.updatePrice("TSLA (Tesla)", 210.20)
}