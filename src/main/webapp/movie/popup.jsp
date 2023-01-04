<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://pyscript.net/latest/pyscript.css" />
<script defer src="https://pyscript.net/latest/pyscript.js"></script>
<%
	String movie_name = request.getParameter("movie_name");
%>
<py-config>
	packages = ["pandas","numpy","scikit-learn","pyodide.http"]
</py-config>
<py-script>
import pandas as pd
import numpy as np
from pyodide.http import open_url
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
tfidf = TfidfVectorizer(stop_words='english')
df = pd.read_csv(open_url("http://localhost:8080/FinalTestWeb/data/final_data.csv"))

count = CountVectorizer(stop_words='english')
count_matrix = count.fit_transform(df['soup'])

cosine_sim2 = cosine_similarity(count_matrix, count_matrix)
df = df.reset_index()
indices = pd.Series(df.index, index = df['title'])

def get_recommendations_keyword(title, cosine_sim2 = cosine_sim2):
	idx = indices[title]
	sim_scores = list(enumerate(cosine_sim2[idx]))
	sim_scores = sorted(sim_scores, key = lambda x : x[1], reverse = True)
	sim_scores = sim_scores[1:11]
	movie_indices = [i[0] for i in sim_scores]
	return df['title'].iloc[movie_indices]

movies = get_recommendations_keyword("<%=movie_name %>", cosine_sim2)
movies = list(movies)
</py-script>
<style type="text/css">
	body {
		background-color : #202124;
		color : white;
	}
	
	a {
		color : white;
	}
</style>
</head>
<body>
<h2><%=movie_name %>와/과 비슷한 영화 10개를 추천해드립니다.</h2>
<py-script>
for i in movies:
	print(i)
</py-script>
</body>
</html>