package org.anirudh.redquark.lucenestarter.document;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * This class is ued to read the indices created by the Lucene
 * 
 * @author anirshar
 *
 */
public class LuceneReadIndex {

	/**
	 * Path of the file where the indices are created. Documents are indexed here
	 * and they are going to be read from here only
	 */
	private static final String INDEX_DIR = "C:\\Users\\anirshar\\Documents\\oks_workspace\\lucene-starter\\index_directory";

	public static void main(String[] args) {

		try {
			/**
			 * Getting an IndexSearcher
			 */
			IndexSearcher searcher = createSearcher();

			/**
			 * Searching by id
			 */
			TopDocs idDocs = searchById(2, searcher);

			/**
			 * Getting the total number of hits and printing
			 */
			System.out.println("Total results: " + idDocs.totalHits);

			/**
			 * The method scoreDoc represents the top hits for the query
			 */
			for (ScoreDoc sDoc : idDocs.scoreDocs) {

				Document doc = searcher.doc(sDoc.doc);
				System.out.println(String.format(doc.get("firstName")));
			}

			/**
			 * Search by firstName
			 */
			TopDocs nameDocs = searchByName("Bruce", searcher);

			/**
			 * Getting the total number of hits and printing
			 */
			System.out.println("Total results: " + nameDocs.totalHits);

			/**
			 * The method scoreDoc represents the top hits for the query
			 */
			for (ScoreDoc sDoc : nameDocs.scoreDocs) {

				Document doc = searcher.doc(sDoc.doc);
				System.out.println(String.format(doc.get("website")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Applications usually need only call the inherited search(Query,int) or
	 * search(Query,Filter,int) methods. For performance reasons, if the index is
	 * unchanging, we should share a single IndexSearcher instance across multiple
	 * searches instead of creating a new one per-search.
	 * 
	 * IndexSearcher instances are completely thread safe, meaning multiple threads
	 * can call any of its methods, concurrently. If your application requires
	 * external synchronization, you should not synchronize on the IndexSearcher
	 * instance; use your own (non-Lucene) objects instead.
	 * 
	 * @return IndexSearcher
	 */
	private static IndexSearcher createSearcher() {

		try {
			/**
			 * A Directory is a flat list of files. Files may be written once, when they are
			 * created. Once a file is created it may only be opened for read, or deleted.
			 * Random access is permitted both when reading and writing.
			 */
			Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

			/**
			 * DirectoryReader is an implementation of CompositeReader that can read indexes
			 * in a Directory
			 * 
			 * IndexReader is an abstract class, providing an interface for accessing an
			 * index.
			 */
			IndexReader reader = DirectoryReader.open(directory);

			/**
			 * Searches for the indices via IndexReader
			 */
			IndexSearcher searcher = new IndexSearcher(reader);

			return searcher;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * This method returns the top results after running the query based on id
	 * 
	 * @param id
	 * @param searcher
	 * @return TopDocs
	 */
	private static TopDocs searchById(Integer id, IndexSearcher searcher) {

		try {
			/**
			 * QueryParser is used to create a Query object on the provided field
			 */
			QueryParser queryParser = new QueryParser("id", new StandardAnalyzer());

			/**
			 * Creating a Query object using the QueryParser's instance and the field on
			 * which query is to be run
			 */
			Query query = queryParser.parse(id.toString());

			/**
			 * Represents hits returned by IndexSearcher.search(Query,int).
			 * 
			 * The second int argument represents the total number of hits for the query.
			 * 
			 */
			TopDocs hits = searcher.search(query, 10);

			return hits;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method returns the top results after running the query based on
	 * firstName
	 * 
	 * @param firstName
	 * @param searcher
	 * @return TopDocs
	 */
	private static TopDocs searchByName(String firstName, IndexSearcher searcher) {

		try {

			/**
			 * QueryParser is used to create a Query object on the provided field
			 */
			QueryParser queryParser = new QueryParser("firstName", new StandardAnalyzer());

			/**
			 * Creating a Query object using the QueryParser's instance and the field on
			 * which query is to be run
			 */
			Query query = queryParser.parse(firstName);

			/**
			 * Represents hits returned by IndexSearcher.search(Query,int).
			 * 
			 * The second int argument represents the total number of hits for the query.
			 * 
			 */
			TopDocs hits = searcher.search(query, 10);

			return hits;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
