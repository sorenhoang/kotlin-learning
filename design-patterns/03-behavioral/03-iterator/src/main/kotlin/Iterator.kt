package behavioral.iterator

// ========================================================
// 1. THE DATA MODEL
// ========================================================
class Song(val title: String, val artist: String)

// ========================================================
// 2. THE ITERATOR INTERFACE
// ========================================================
interface MyIterator<T> {
    fun hasNext(): Boolean

    fun next(): T
}

// ========================================================
// 3. THE COLLECTION INTERFACE (Aggregate)
// ========================================================
interface MyCollection<T> {
    fun createIterator(): MyIterator<T>
}

// ========================================================
// 4. CONCRETE ITERATOR & COLLECTION
// ========================================================

// Concrete Iterator for traversing songs
class SongIterator(private val songs: List<Song>) : MyIterator<Song> {
    private var position = 0

    override fun hasNext(): Boolean {
        return position < songs.size
    }

    override fun next(): Song {
        val song = songs[position]
        position++
        return song
    }
}

// Concrete Collection holding the actual song data
class Playlist : MyCollection<Song> {
    private val songList = mutableListOf<Song>()

    fun addSong(
        title: String,
        artist: String,
    ) {
        songList.add(Song(title, artist))
    }

    // Factory method to supply the correct implementation of Iterator
    override fun createIterator(): MyIterator<Song> {
        return SongIterator(songList)
    }
}

fun main() {
    println("Hello Iterator Design Pattern")

    // 1. Create a playlist and populate it with some music data
    val myPlaylist =
        Playlist().also {
            it.addSong("Shape of You", "Ed Sheeran")
            it.addSong("Blinding Lights", "The Weeknd")
            it.addSong("Stay", "Justin Bieber")
        }

    println("=== STARTING PLAYLIST TRAVERSAL ===")

    // 2. Request an iterator from the collection
    val iterator: MyIterator<Song> = myPlaylist.createIterator()

    // 3. Traverse the items cleanly without knowing the playlist's inner internal structure (List/Array)
    while (iterator.hasNext()) {
        val song = iterator.next()
        println("🎵 Playing: '${song.title}' by [${song.artist}]")
    }

    println("=== PLAYLIST FINISHED ===")
}
