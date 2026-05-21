package behavioral.template_method

// ========================================================
// 1. THE ABSTRACT CLASS (Defines the Template Method)
// ========================================================
abstract class DataMiner {

    // This is the TEMPLATE METHOD.
    // It is final (not open) to prevent subclasses from changing the algorithm skeleton.
    fun mineData(filePath: String) {
        openFile(filePath)
        val rawData = extractData(filePath) // Abstract step defined by subclasses
        analyzeData(rawData)
        closeFile()
    }

    // Common steps implemented directly in the base class
    private fun openFile(path: String) {
        println("📂 FileSystem: Successfully opened file at path: '$path'")
    }

    private fun closeFile() {
        println("🔒 FileSystem: Safely closed the file connection.\n")
    }

    private fun analyzeData(data: String) {
        println("📊 AnalyticsEngine: Running AI algorithm to analyze extracted text: \"$data\"")
    }

    // 2. ABSTRACT STEPS - Subclasses MUST implement these specific details
    protected abstract fun extractData(path: String): String
}

// ========================================================
// 3. CONCRETE CLASSES - Implementing the custom steps
// ========================================================

// Concrete Miner A: For PDF Files
class PdfDataMiner : DataMiner() {
    override fun extractData(path: String): String {
        println("📄 PdfMiner: Parsing PDF binary streams and scanning fonts...")
        return "Raw text from PDF file data"
    }
}

// Concrete Miner B: For CSV Files
class CsvDataMiner : DataMiner() {
    override fun extractData(path: String): String {
        println("📊 CsvMiner: Splitting comma-separated values row by row...")
        return "Raw text from CSV rows and columns"
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    println("=== TEST 1: MINING DATA FROM PDF ===")
    val pdfMiner: DataMiner = PdfDataMiner()
    // The client calls the template method, NOT the individual steps
    pdfMiner.mineData("invoice_report.pdf")

    println("=== TEST 2: MINING DATA FROM CSV ===")
    val csvMiner: DataMiner = CsvDataMiner()
    csvMiner.mineData("user_analytics_data.csv")
}