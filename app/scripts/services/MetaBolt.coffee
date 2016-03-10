###!
Copyright (c) 2002-2016 "Neo Technology,"
Network Engine for Objects in Lund AB [http://neotechnology.com]

This file is part of Neo4j.

Neo4j is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
###

'use strict';

angular.module('neo4jApp.services')
  .factory 'MetaBolt', [
    '$q'
    'CypherResult'
    'Bolt'
    ($q, CypherResult, Bolt) ->

      callProc = (query) ->
        statements = if query then [{statement: "CALL " + query}] else []
        result = Bolt.transaction(statements)

        q = $q.defer()
        result.promise.then(
          (res) =>
            q.resolve(res.records)
        ,
          (res) ->
            q.resolve([])
        )
        q.promise


      fetch: ->
        q = $q.defer()
        $q.all([
          callProc("db.labels"),
          callProc("db.relationshipTypes"),
          callProc("db.propertyKeys")
        ]).then((data) ->
          q.resolve(Bolt.constructMetaResult data[0], data[1], data[2])
        )
        q.promise

]

