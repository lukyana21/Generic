import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

//Pengenalan Generic

//*Contoh yang bukan Generic*//
//class Data(val data: Any)
//
//fun main(){
//    val dataString = Data("Luky")
//    val valueString: String = dataString.data as String
//
//    val dataInt = Data(21)
//    val valueInt: Int = dataInt.data as Int
//}

//*Contoh Generic*//
//<T> adalah Generic Type
//<type data yang dimau>
//
//class Data<T>(val data: T)
//
//fun main(){
//    val dataString = Data<String>("Luky")
//    val valueString: String = dataString.data
//    println(valueString)
//
//    val dataInt = Data<Int>(10)
//    val valueInt: Int = dataInt.data
//    println(valueInt)
//}


//Generic Type adalah class atau interface yang memiliki parameter type
//Nama generic parameter type yang biasa digunakan adalah:
//1. E -> Element (biasa digunakan di collection atau striktir data)
//2. K -> Key
//3. N -> Number
//4. T -> Type
//5. V -> Value

//class MyData<T>(val firstData: T){
//    fun getData(): T = firstData
//    fun printlnData(){
//        return println("Data is $firstData")
//    }
//}
//
//fun main(){
//    val myDataString: MyData<String> = MyData<String>("Luky")
//    myDataString.printlnData()
//
//    val myDataInt: MyData<Int> = MyData<Int>(21)
//    myDataInt.printlnData()
//}

//Multiple Parameter Type
// Parameter type bileh lebih dari satu tapi harus menggunakan nama type yang berbeda
//class MyData<T, U>(val firstData: T, val secondData: U){
//    fun getData(): T = firstData
//    fun getSecond(): U = secondData
//
//    fun printlnData(){
//        println("Data is $firstData $secondData")
//    }
//}
//
//fun main(){
//    val myDataDiri: MyData<String, Int> = MyData<String, Int>("Luky", 21)
//    myDataDiri.printlnData()
//    println("namanya ${myDataDiri.getData()}")
//    println("umurnya ${myDataDiri.getSecond()}")
//}

//Generic Function
//class Function(val name: String){
//    fun <T> sayHello(param: T){
//        println("Hello $param, my name is $name")
//    }
//}
//fun main(){
//    val function = Function("Luky")
//
//    function.sayHello<String>("Budi")
//    function.sayHello("Budi")
//
//    function.sayHello<Int>(21)
//    function.sayHello(21)
//}

//invarian
//tidak boleh disubtitusi dengan subtype(child) atau supertype(parent)
//class Invarian<T>(val data: T)
//
//fun main(){
//    val invarianString = Invarian("String")
//    val invarianAny: Invarian<Any> = invarianString ////tidak bisa karena bahaya
//}

//Covariant
//kita bisa melakukan subtitusi subtype dengan supertype
//Untuk memberitahu bahwa generic parameter type tersebut adalah covariant, kita perlu menggunakan kata kunci out
//hanya bisa return tidak bisa menerima input parameter atau data
//class Covarian<out T>(val data: T){
//    fun data(): T{
//        return data
//    }
//}
//fun main(){
//    val data1: Covarian<String> = Covarian("Luky")
//    val data2: Covarian<Any> = data1
//
//    println(data2.data())
//}

//Contravariant
//kebalikan dari covariant
//untuk memberitahu bahwa generic parameter type tersebut adalah covariant, kita perlu menggunakan kata kunci in
//tidak boleh mengembalikan data generic typenya
//class Contravariant<in T>{
//    fun sayHello(name: T){
//        return println("Hello $name")
//    }
//}
//fun main(){
//    val data1: Contravariant<Any> = Contravariant()
//    val data2: Contravariant<String> = data1
//
//    data2.sayHello("Luky")
//}

//Generic Constraints
//open class Employee
//class Manager: Employee()
//class VicePresident: Employee()
//class Company<T: Employee>(val employee: T)
//fun main(){
//    val data1 = Company(Employee())
//    val data2 = Company(Manager())
//    val data3 = Company(VicePresident())
//}
//*Where Keyword*//
//interface CanSayHello{
//    fun sayHello(name: String)
//}
//open class Employee
//class Manager: Employee()
//class VicePresident: Employee(), CanSayHello{
//    override fun sayHello(name: String){
//        println("Hello $name, I'm vice president")
//    }
//}
//class Company<T>(val employee: T)where T: Employee, T: CanSayHello
//fun main(){
//    val data1 = Company(Employee())////Error CanSayHello
//    val data2 = Company(Manager())////Error CanSayHello
//    val data3 = Company(VicePresident())
//    val data4 = Company("String")//error not Employee
//}

//Type Projection
//menambahkan informasi covariant atau contravariant
//class Container<T>(var data: T)
//fun copyContainer(from: Container<out Any>, to: Container<in Any>){
//    to.data = from.data
//}
//fun main(){
//    val container1 = Container("Luky")
//    val container2: Container<Any> = Container("Ana")
//
//    copyContainer(container1, container2)
//    println(container1.data)
//    println(container2.data)
//}

//Star Projection
//fun displayLength(array: Array<*>){
//    println("Total array adalah ${array.size}")
//}
//fun main(){
//    val arrayInt: Array<Int> = arrayOf(1,2,3,4,5,6,7)
//    val arrayString: Array<String> = arrayOf("luky","ana","adi","pratama")
//
//    displayLength(arrayInt)
//    displayLength(arrayString)
//}

//Type Erasure
//proses pengecekan generic pada saat compile yime, dan menghiraukan pengecekan pada saat runtime
//karena informasi generic hilang ketika sudah menjadi binary file
//Oleh karena itu, konversi tipe data generic akan berbahaya jika dilakukan secara tidak bijak
//class TypeErasure<T>(param: T){
//    private val data: T = param
//    fun getData(): T = data
//}
//fun main(){
//    val data1 = TypeErasure<String>("Luky")
//    val dataString: String = data1.getData()
//    println(dataString)
//
//    val data2: TypeErasure<Int> = data1 as TypeErasure<Int>
//    val dataInt = data2.getData() ////error disini
//    println(dataInt)
//}

//Comparable Interface
//class Fruit(val name: String, val quantity: Int) : Comparable<Fruit> {
//
//    override fun compareTo(other: Fruit): Int{
//        return quantity.compareTo((other.quantity))
//    }
//}
//fun main(){
//    val fruit1 = Fruit("Apple",100)
//    val fruit2 = Fruit("Apple", 10)
//
//    println(fruit1 > fruit2)
//    println(fruit1 >= fruit2)
//    println(fruit1 < fruit2)
//    println(fruit1 <= fruit2)
//}

//ReanOnlyProperty Interface
//ReadOnlyProperty bisa digunakan sebagai delegate, sehingga sebelum data dikembalikan, kita bisa melakukan sesuatu, atau bahkan mengubaj value si property
//class LogReadOnlyProperty(val data: String): ReadOnlyProperty<Any, String>{
//    var counter: Int = 0
//    override fun getValue(thisRef: Any, property: KProperty<*>): String {
//        println("Access property ${property.name} with value $data")
//        counter++
//        return data.toUpperCase() + counter
//    }
//}
//class NameWithLog(param: String){
//    val name: String by LogReadOnlyProperty(param)
//}
//fun main(){
//    val nameWithLog = NameWithLog("Luky Ana")
//
//    println(nameWithLog.name)
//    println(nameWithLog.name)
//}

//ReadWriteProperty Interface
//class StringLogReadWriteProperty(var data: String): ReadWriteProperty<Any, String>{
//    override fun getValue(thisRef: Any, property: KProperty<*>): String {
//        println("Get property ${property.name} with value $data")
//        return data
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
//        println("Set property ${property.name} from $data to $value")
//        data = value
//    }
//}
//class Person(param: String){
//    var name: String by StringLogReadWriteProperty(param)
//}
//fun main(){
//    val person = Person("luky")
//    println(person.name)
//    person.name = "Ana"
//    println(person.name)
//}

//ObservableProperty Class
//class LogObservableProperty<T>(param: T): ObservableProperty<T>(param){
//
//    override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean {
//        println("Before change ${property.name} from $oldValue to $newValue")
//        return true
//    }
//
//    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
//        println("After change ${property.name} from $oldValue to $newValue")
//    }
//}
//class Car(brand: String, year: Int){
//    var brand: String by LogObservableProperty(brand)
//    var year: Int by LogObservableProperty(year)
//}
//fun main(){
//    var car = Car("Toyota", 2019)
//
//    car.brand = "Wuling"
//    println(car.brand)
//
//    car.year = 2020
//    println(car.year)
//}

//Generic Extension Function
//Generic juga bisa digunakan pada extension functuin
//Dengan begitu kita bisa memilih jenis generic parameter type apa yang bisa menggunakan extension function tersebut
//class Data<T>(val data: T)
//fun Data<String>.print(){
//    val string = this.data
//    println("String value is $string")
//}
//fun main(){
//    val data1: Data<Int> = Data(1)
//    val data2: Data<String> = Data("Luky")
//
////    data1.print()//error karena ini buka string
//    data2.print()
//}