package structural.composite

interface FileSystemComponent {
    val name: String

    fun printStructure(indent: String)

    fun getSize(): Int
}

class File(override val name: String, private val size: Int) : FileSystemComponent {
    override fun printStructure(indent: String) {
        println("$indent📄 $name ($size KB)")
    }

    override fun getSize(): Int = size
}

class Folder(override val name: String) : FileSystemComponent {
    private val children = mutableListOf<FileSystemComponent>()

    fun addComponent(component: FileSystemComponent) {
        children.add(component)
    }

    fun removeComponent(component: FileSystemComponent) {
        children.remove(component)
    }

    override fun printStructure(indent: String) {
        println("$indent📁 $name/")
        for (child in children) {
            child.printStructure("$indent   ") // Tăng thụt lề cho thư mục con
        }
    }

    override fun getSize(): Int {
        var totalSize = 0
        for (child in children) {
            totalSize += child.getSize()
        }
        return totalSize
    }
}

fun main(args: Array<String>) {
    println("Hello Composite Design Pattern")

    val file1 = File("Singleton.kt", 4)
    val file2 = File("README.md", 2)
    val file3 = File("Adapter.kt", 5)

    val singletonFolder =
        Folder("01-singleton").also {
            it.addComponent(file1)
            it.addComponent(file2)
        }

    val adapterFolder =
        Folder("01-adapter").also {
            it.addComponent(file3)
        }

    val creationalFolder =
        Folder("01-creational").also {
            it.addComponent(singletonFolder)
        }

    val structuralFolder =
        Folder("02-structural").also {
            it.addComponent(adapterFolder)
        }

    val rootDesignPattern =
        Folder("design-patterns").also {
            it.addComponent(creationalFolder)
            it.addComponent(structuralFolder)
        }

    println("\n--- Structure Pattern ---")
    rootDesignPattern.printStructure("")

    println("\n--- Calculate Size ---")
    println("Size of Folder [01-singleton]: ${singletonFolder.getSize()} KB")
    println("total size of all [design-patterns]: ${rootDesignPattern.getSize()} KB")
}
