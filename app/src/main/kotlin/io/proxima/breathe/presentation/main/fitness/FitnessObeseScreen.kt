package io.proxima.breathe.presentation.main.fitness

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.fitness.FitnessScreenState
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@Composable
fun FitnessObeseScreen(fitnessState: FitnessScreenState) {
    val bmi = fitnessState.bmi
    val setupDate = fitnessState.setupDate

    // Calculate day number (if setupDate is set, otherwise default to 1)
    val currentTime = System.currentTimeMillis()
    val dayNumber = if (setupDate > 0) {
        ((currentTime - setupDate) / TimeUnit.DAYS.toMillis(1)) + 1
    } else {
        1L
    }

    // Determine the current meal label based on current hour
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val meal = when {
        currentHour < 11 -> "Breakfast"
        currentHour < 16 -> "Lunch"
        else -> "Dinner"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image fills the screen.
        Image(
            painter = painterResource(id = R.drawable.fitness_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Scrollable content overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp, bottom = 70.dp, start = 28.dp, end = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Changed to Top to let content scroll naturally
        ) {
            // Top header
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Fitness Assist",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Helps you know your fitness results and gives suggestions",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // BMI Condition Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Obese",
                        style = BreathTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "BMI: ${bmi.roundToInt()}",
                        style = BreathTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Day: $dayNumber",
                        style = BreathTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp)
                    .clip(RoundedCornerShape(50)),
                color = BreathDefaultColors.background
            )

            // Workout and Diet Sections
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Day $dayNumber workout",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Workout Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (dayNumber == 1L) {
                            Text(
                                text = """
                    Day 1: Upper Body (Push & Pull)
                    1. Push-ups 
                       • Sets: 3 
                       • Reps: 8-12 
                       • (Modify by doing knee push-ups if needed.)
                    2. Tricep Dips (using a sturdy chair or bench)
                       • Sets: 3
                       • Reps: 10-15
                    3. Resistance Band Chest Press
                       • Sets: 3
                       • Reps: 12-15
                    4. Resistance Band Rows
                       • Sets: 3
                       • Reps: 12-15
                    5. Planks
                       • Sets: 3
                       • Time: 30-60 sec per set (Increase as you get stronger)
                    6. Bicep Curls (with resistance band or dumbbells)
                       • Sets: 3
                       • Reps: 12-15
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 15.sp
                            )
                        } else {
                            Text(
                                text = "Workout plan for Day $dayNumber will be updated soon.",
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Diet for $meal:",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Diet Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        when (meal) {
                            "Breakfast" -> {
                                Text(
                                    text = """
                        Breakfast: 
                        1. Oatmeal with Full-Fat Milk, Banana, and Peanut Butter 
                        2. Greek Yogurt with Honey, Granola, and Mixed Nuts 
                        3. Scrambled Eggs with Avocado on Toast 
                        4. Smoothie (Milk, Banana, Peanut Butter, Protein Powder) 
                        5. Cottage Cheese with Fruit and Chia Seeds
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 12.sp
                                )
                            }
                            "Lunch" -> {
                                Text(
                                    text = """
                        Lunch: 
                        1. Grilled Chicken Thighs with Quinoa and Avocado 
                        2. Sweet Potato, Roasted Vegetables, and Hummus 
                        3. Whole Grain Wrap with Turkey, Avocado, and Cheese 
                        4. Rice with Beans, Grilled Chicken, and Guacamole 
                        5. Cottage Cheese with Avocado, Nuts, and Tomatoes
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 15.sp
                                )
                            }
                            else -> { // Dinner
                                Text(
                                    text = """
                        Dinner: 
                        1. Salmon with Sweet Potato and Steamed Vegetables 
                        2. Stir-Fried Chicken with Brown Rice and Mixed Vegetables 
                        3. Pasta with Ground Beef, Olive Oil, and Parmesan 
                        4. Baked Chicken Thighs with Quinoa and Roasted Broccoli 
                        5. Beef Stew with Potatoes, Carrots, and Peas
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


