MagicMapper is a lightweight framework for auto-mapping POJOs. It is very useful when you have data access and/or transition objects and you want to map one to another.

MagicMapper supports a direct auto-mapping (hereinafter called "perfect match"). It means that without any configuration you can one object to object of another class by their fields equal by type and name. It also supports configurable mapping. It means that in your application entry point, you need to configure how object from one class maps to object to another class, by either providing field names equation or by delegating a certain behavior.

# Perfect Match

Consider having the following classes with equal definitions: **AuthorEntity and AuthorDto**

**AuthorEntity.java**

    public class AuthorEntity {
        private int id;
        private String name;
        private Date bornOn;
    }

**AuthorDto.java**

    public class AuthorDto {
        private int id;
        private String name;
        private Date bornOn;
    }

If we have an object of type **AuthorEntity** and we need to make object of type **AuthorDto** with the same values, we only need to call the existing `map()` method in the `MagicMapping` interface:

**AuthorEntity creation:**

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    Date bornOn = sdf.parse("06.06.2006 03:00:00");
    AuthorEntity entity = new AuthorEntity(6, "John", bornOn);

**AuthorDto mapping:**

    MagicMapper mapper = new ANSRMagicMapper();
    AuthorDto dto = mapper.map(entity, AuthorDto.class);

We can also specify that later in the application we will need that kind of mapping, but it's not necessary in "perfect match" scenario:

    mapper
        .from(AuthorEntity.class)
        .toFinal(AuthorDto.class);

    AuthorDto dto = mapper.map(entity, AuthorDto.class);


# Name Differences

It is very common that the target and the source class has name differences in the field definition. MagicMapper supports configuration either by providing the field name mappings, or by providing a whole delegate.

## Providing Field Name Mappings

You can provide field name mappings by calling the `forRule()` method after defining the `from()`->`to()` association and providing instance of one of the implementations of the `FieldMap` interface. For example `SimpleField` class. 

Let's say we have the following definitions of both classes:


**AuthorEntity.java**

    public class AuthorEntity {
        private int id;
        private String authorName;
        private Date date;
    }

**AuthorDto.java**

    public class AuthorDto {
        private int id;
        private String name;
        private Date bornOn;
    }
    
Then you need to put the following configuration in your application startup:

     mapper
        .from(AuthorEntity.class)
        .to(AuthorDto.class)
        .forRule(
            new SimpleField("id", "id"),
            new SimpleField("authorName", "name"),
            new SimpleField("date", "bornOn")
        );

Again fetching the **AuthorDto** instance is done by calling the same `map()` method:

    AuthorDto dto = mapper.map(entity, AuthorDto.class);

## Providing a Delegate

It's very verbose providing strings and once a field name is changed, you may forgot to change the string configuration, thus the mapping might fail (and in some point just silently). Thus we support providing a delegate, which might be much more strongly typed. This, of course, needs your class definitions to provide a public data manipulation to this fields either by setting them public (not recommended) or by accessors.

Let's expand our class definitions by providing properties (accessor/mutators):

**AuthorEntity.java**

    public class AuthorEntity {
        private int id;
        private String authorName;
        private Date date;
    
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getAuthorName() {
            return authorName;
        }
    
        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }
    
        public Date getDate() {
            return date;
        }
    
        public void setDate(Date date) {
            this.date = date;
        }
    }

**AuthorDto.java**

    public class AuthorDto {
        private int id;
        private String name;
        private Date bornOn;

        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public Date getBornOn() {
            return bornOn;
        }
    
        public void setBornOn(Date bornOn) {
            this.bornOn = bornOn;
        }
    }

Now we can use the overload of the `forRule()` method which accepts an implementation of the functional interface `RuleLambda` which `applyRule()` methods accepts three parameters that will be injected by the `MagicMapper`: **Source object**, **Target object** and **an implementation of the magic mapper** - `(source, target, mapper) -> { }`.

    mapper
        .from(AuthorEntity.class)
        .to(AuthorDto.class)
        .forRule((entity, dto, magicMapper) -> {
            dto.setId(entity.getId());
            dto.setName(entity.getAuthorName());
            dto.setBornOn(entity.getDate());
        });

# Complex Objects

All works fine before your objects start to have pointers to another objects and you want to map them too. There is no magic here. You need to provide configuration for all of your navigation properties if you want to map them. Otherwise they won't be mapped. The `MagicMapper` can reuse the provided before configuration if you tell them so. There are two ways again - by providing magic strings or consumers.

## Providing Field Name Mappings

Let's expand our authors to have a navigation to a book. Then we need **BookEntity** and **BookDto** classes

**BookEntity.java**

    public class BookEntity {
        private long id;
        private String bookName;
    }

**BookDto.java**

    public class BookEntity {
        private long id;
        private String name;
    }

They also have appropriate getters and setters but for the sake of simplifying they are not in the above example.

Now we need to add a field to our authors:

**AuthorEntity.java**

    private BookEntity bookEntity;

**AuthorDto.java**

    private BookDto bookDto;

And appropriate getters and setters too.

All we need to do is to provide configuration for books too. Then for the complex type fields (such as **BookEntity** and **BookDto**) we need to use the implementation of the `FieldMap` interface called `ComplexField` rather than `SimpleField`. It asks for third parameter in the constructor which is a delegate `(sourceObject) -> targetObject`. You might provide your own way of creating `targetObject` from `sourceObject` or reuse an existing configuration by calling the mapper's `getExistingBinding(Source.class, Target.class)` method

**Providing Configuration for Books:**

    mapper
        .from(BookEntity.class)
        .to(BookDto.class)
        .forRule(
            new SimpleField("id", "id"),
            new SimpleField("bookName", "name")
        );

**Providing Configuration for Authors:**

    mapper
        .from(AuthorEntity.class)
        .to(AuthorDto.class)
        .forRule(
            new SimpleField("id", "id"),
            new SimpleField("authorName", "name"),
            new SimpleField("date", "bornOn"),
            new ComplexField("bookEntity", "bookDto", this.mapper.getExistingBinding(BookEntity.class, BookDto.class))
         );


## Providing a Delegate

Again, removing magic strings can be done by providing a delegate. Since books should be configured at the time you need an author, you can safely reuse the `map()` method in your delegate.

**Providing Configuration for Books:**

    mapper
        .from(BookEntity.class)
        .to(BookDto.class)
        .forRule((entity, dto, magicMapper) -> {
            dto.setId(entity.getId());
            dto.setName(entity.getBookName());
         });

**Providing Configuration for Authors:**

    mapper
        .from(AuthorEntity.class)
        .to(AuthorDto.class)
        .forRule((entity, dto, magicMapper) -> {
            dto.setId(entity.getId());
            dto.setName(entity.getAuthorName());
            dto.setBornOn(entity.getDate());
            dto.setBookDto(
                magicMapper.map(entity.getBookEntity(), BookDto.class)
            );
         });

And voila! You have mapped your entities.

Currently `MagicMapper` does not support collection transforming. It will support it in the near future.
