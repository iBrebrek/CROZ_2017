create table categories (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255)
);

create table jokes (
	id SERIAL PRIMARY KEY,
	category_id INTEGER references categories(id),
	content TEXT,
	likes INTEGER,
	dislikes INTEGER
);
	