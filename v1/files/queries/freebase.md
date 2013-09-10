Book publications:

	[{
	   "type": "/book/author",
	   "mid": null,
	   "name": [{}],
	   "/common/topic/alias": [{}],
	   "/book/author/works_written": [{
  	   		"mid": null,
	   		"/book/book/genre": "Science Fiction",
 	   			"date_of_first_publication": {
  	      		"value": null,
	      		"optional": false
 	   		},
	   		"name": [{}],
 	   		"/common/topic/alias": [{}],
	   		"limit": 1
	  	}],
	}]

Company foundations:

	[{
		"id": "/m/01mf0",
		"/business/industry/companies": [{
			"mid": null,
			"name": [{}],
			"/common/topic/alias": [{}],
	    	"/organization/organization/place_founded": [{
	    		"mid": null,
	      		"name": [{}],
 	      		"/common/topic/alias": [{}]
	    	}],
	    	"/organization/organization/date_founded": {
	      		"value": null,
	      		"optional": false
	    	},
	  	}]
	}]

Actor marriages:

	[{
		"type": "/people/person",
		"profession": "Actor",
		"/people/person/spouse_s": [{
			"spouse": [{
				"name": [{}],
				"/common/topic/alias": [{}],
				"mid": null
    		}],
    		"from": null,
    		"from>": "2003-01-01",
    		"to": {
      			"value": null,
      			"optional": false
    		}
  		}],
	}]

Nobel prize winners:
	
	[{
		"type": "/award/award_honor",
		"award": {
			"mid": "/m/05f3q",
			"name": [{}],
			"/common/topic/alias": [{}]
		},
		"award_winner": [{
			"mid": null,
			"name": [{}],
			"/common/topic/alias": [{}]
  		}],
  		"year": null,
  		"year>": "0000",
	}]

Company acquisitions:

	[{
		"/organization/organization/acquired_by": [{
			"/business/acquisition/acquiring_company": [{
				"mid": null,
      			"name": [{}],
				"/common/topic/alias": [{}]
    		}],
    		"/business/acquisition/company_acquired": [{
				"mid": null,
				"name": [{}],
				"/common/topic/alias": [{}]
			}],
    		"/business/acquisition/date": {
				"value": null,
				"optional": false
			}
		}],
	}]