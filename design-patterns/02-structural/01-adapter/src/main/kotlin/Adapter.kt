package structural.adapter

// Current Structure
interface JsonAnalytics {
    fun displayData(jsonData: String)
}

class MyDashboard : JsonAnalytics {
    override fun displayData(jsonData: String) {
        println("📊 Dashboard: show data from: $jsonData")
    }
}

// New Structure
class AdvancedXmlAnalyticsLibrary {
    fun analyzeXmlData(xmlData: String) {
        println("⚙️ Library: Analytics data from XML: $xmlData")
    }
}

// Adapter
class XmlToJsonAdapter(private val xmlLibrary: AdvancedXmlAnalyticsLibrary) : JsonAnalytics {
    override fun displayData(jsonData: String) {
        val convertedXml = convertJsonToXml(jsonData)
        println("🔄 Adapter: Converting to XML...")

        xmlLibrary.analyzeXmlData(convertedXml)
    }

    private fun convertJsonToXml(json: String): String {
        val value = json.substringAfter(": \"").substringBefore("\"}")
        return "<data>$value</data>"
    }
}

fun main() {
    println("Hello Adapter Design Pattern")

    val jsonData = "{\"data\": \"DoanhThuThang5\"}"

    println("=== 1. RUN IN OLD SYSTEM ===")
    val oldDashboard: JsonAnalytics = MyDashboard()
    oldDashboard.displayData(jsonData)

    println("\n=== 2. INTEGRATE NEW LIBRARY VIA ADAPTER ===")
    val partnerLibrary = AdvancedXmlAnalyticsLibrary()

    val adapter: JsonAnalytics = XmlToJsonAdapter(partnerLibrary)

    adapter.displayData(jsonData)
}
