package behavioral.memento

import java.util.Stack

// ========================================================
// 1. THE MEMENTO - Stores the immutable state of the Originator
// ========================================================
class EditorMemento(val textState: String) {
    // This object is simple and immutable. No one can change the state inside.
}

// ========================================================
// 2. THE ORIGINATOR - The object whose state needs to be saved/restored
// ========================================================

class TextEditor {
    private var text: String = ""

    fun type(appendText: String) {
        text += appendText
    }

    fun printContent() {
        println("📝 Editor Content: \"$text\"")
    }

    fun save(): EditorMemento {
        println("📸 [Originator] Snapshot captured.")
        return EditorMemento(text)
    }

    fun restore(memento: EditorMemento) {
        text = memento.textState
        println("🔄 [Originator] State successfully restored.")
    }
}

// ========================================================
// 3. THE CARETAKER - Responsible for keeping track of Memento history
// ========================================================
class HistoryManager {
    private val historyStack = Stack<EditorMemento>()

    fun pushSnapshot(memento: EditorMemento) {
        historyStack.push(memento)
    }

    fun popSnapshot(): EditorMemento? {
        if (historyStack.isNotEmpty()) {
            return historyStack.pop()
        }
        return null
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // Initialize the Editor (Originator) and the History (Caretaker)
    val editor = TextEditor()
    val history = HistoryManager()

    println("=== STEP 1: TYPING AND SAVING ===")
    editor.type("Hello ")
    editor.printContent()
    history.pushSnapshot(editor.save()) // Save state "Hello "

    println("\n=== STEP 2: TYPING MORE AND SAVING ===")
    editor.type("World! ")
    editor.printContent()
    history.pushSnapshot(editor.save()) // Save state "Hello World! "

    println("\n=== STEP 3: MAKING A MISTAKE ===")
    editor.type("This is a wrong sentence...")
    editor.printContent()

    println("\n=== STEP 4: FIRST UNDO ===")
    // Pop the last snapshot and give it back to the editor to restore
    val previousState = history.popSnapshot()
    if (previousState != null) {
        editor.restore(previousState)
    }
    editor.printContent()

    println("\n=== STEP 5: SECOND UNDO ===")
    val oldestState = history.popSnapshot()
    if (oldestState != null) {
        editor.restore(oldestState)
    }
    editor.printContent()
}