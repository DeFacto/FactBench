Prime Minister of Countries:

	PREFIX dbo: <http://dbpedia.org/ontology/>
	PREFIX dbr: <http://dbpedia.org/resource/>
	PREFIX yago: <http://dbpedia.org/class/yago/>
	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
	
	SELECT *
	FROM <http://dbpedia.org>
	WHERE {
		?person dbo:termPeriod ?timePeriod . 
		?timePeriod dbo:office ?office . 
		?timePeriod dbo:activeYearsStartDate ?from . 
		?timePeriod dbo:activeYearsEndDate ?to .
		FILTER (regex(?office, "^Prime Minister of.*"))
	}
	
Births of persons:
	
	PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

	SELECT ?person ?place ?date  
	WHERE {   
	    ?person dbpedia-owl:birthPlace ?place .   
	    ?place rdf:type dbpedia-owl:City  .   
	    ?person dbpedia-owl:birthDate ?date .  
	    ?person dbpedia-owl:numberOfInboundLinks ?personInbound . 
	    ?place dbpedia-owl:numberOfInboundLinks ?placeInbound . 
	}   
	ORDER BY DESC(?personInbound) DESC(?placeInbound)

Actors starring in a movie:
	
	PREFIX dbo: <http://dbpedia.org/ontology/>
	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

	SELECT DISTINCT ?film ?actor ?date 
	WHERE {   
	    ?film dbo:starring ?actor .   
	    ?film rdf:type dbo:Film .  
	    ?film dbo:releaseDate ?date .
	    ?film dbo:numberOfInboundLinks ?filmInbound . 
	}   
	ORDER BY DESC(?filmInbound) ASC(?date)

Deaths of persons:
		
	PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> 
	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

	SELECT ?person ?place ?date
	FROM <http://dbpedia.org>
	WHERE {  
	    ?person dbpedia-owl:deathPlace ?place .  
	    ?place rdf:type dbpedia-owl:City  .  
	    ?person dbpedia-owl:deathDate ?date . 
	    ?person dbpedia-owl:numberOfInboundLinks ?personInbound .
	    ?place dbpedia-owl:numberOfInboundLinks ?placeInbound .
	} 
	ORDER BY DESC(?personInbound) DESC(?placeInbound)
	
NBA team associations of basketball players:
	
	PREFIX dbo: <http://dbpedia.org/ontology/>
	PREFIX dbr: <http://dbpedia.org/resource/>
	PREFIX yago: <http://dbpedia.org/class/yago/>
	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

	SELECT ?player ?timePeriod ?team ?from ?to 
	FROM <http://dbpedia.org> 
	WHERE {
		 ?player dbo:league	dbr:NBA  .
		 ?player dbo:termPeriod ?timePeriod .
		 ?timePeriod dbo:team ?team .
	    ?team rdf:type yago:NationalBasketballAssociationTeams .
		 ?timePeriod dbo:team ?team .
		 ?timePeriod dbo:activeYearsStartYear ?from . 
		 ?timePeriod dbo:activeYearsEndYear ?to .
		 FILTER( xsd:gYear(?from) > "2000"^^xsd:gYear )
	}
		