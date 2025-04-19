package io.duckcat.d.presentation.main.fitness

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
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
import io.duckcat.d.R
import io.duckcat.d.core.ui.theme.BreathDefaultColors
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.presentation.fitness.FitnessScreenState
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@Composable
fun FitnessOverweightScreen(
    fitnessState: FitnessScreenState,
    onEditClick: () -> Unit // callback when edit button is pressed
)  {
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ){
                    // Edit button placed at the top right
                    IconButton(onClick = onEditClick) {
                        Image(
                            painter = painterResource(id = R.drawable.edit_pensil_enhanced),
                            contentDescription = "edit",
                            modifier = Modifier.size(24.dp) // adjust size as needed
                        )

                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Overweight",
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
                    text = "Diet Pro Tips ✨",
                    style = BreathTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Workout Plan Box with increased height and scrollable content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(442.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(2.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(16.dp)) // Add border here
                        .padding(20.dp)
                ){
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (dayNumber == 1L) {
                            Text(
                                text = """
                                    • Hydration: Drink plenty of water (about 2-3 liters daily, depending on your activity 
                                    level). 
                                    • Macronutrient Balance: 
                                    o Protein: Essential for muscle repair and growth. 
                                    o Carbohydrates: Your body's main energy source. 
                                    o Healthy Fats: Support brain health and hormone balance. 
                                    o Fiber: Important for digestion and satiety. 

                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 16.sp
                            )
                        } else {
                            Text(
                                text = "",
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }

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
                    Day 1: Full Body Strength 
• Warm-up (5-10 minutes) 
o Jumping Jacks: 2 minutes to increase your heart rate 
o Arm Circles: 1 minute (30 seconds forward, 30 seconds backward) 
o Bodyweight Squats: 1 minute 
o Leg Swings: 1 minute (30 seconds per leg) 
o Torso Twists: 1 minute (to loosen up the upper body) 
• Workout (3-4 rounds) 
o Push-ups: 10-15 reps 
o Squats: 15-20 reps 
o Glute Bridges: 15-20 reps 
o Plank: 30-45 seconds 
o Mountain Climbers: 30 seconds 
• Cool-down (5-10 minutes)
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
• Scrambled eggs (2-3 eggs) with spinach and tomatoes 
• 1 slice of whole-grain toast or ½ avocado on toast 
• 1 cup of black coffee or green tea (optional) 
• 1 small apple or 1/2 banana
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 12.sp
                                )
                            }
                            "Lunch" -> {
                                Text(
                                    text = """
                       Lunch: 
• Grilled chicken or tofu (4-6 oz) 
• Quinoa or brown rice (1/2 cup cooked) 
• Steamed broccoli and carrots 
• Olive oil and lemon juice dressing on a side salad (lettuce, cucumbers, and tomatoes)
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 15.sp
                                )
                            }
                            else -> { // Dinner
                                Text(
                                    text = """
                        Dinner: 
• Grilled salmon or baked chicken breast (4-6 oz) 
• Sweet potato (1 medium-sized, baked) 
• Steamed green beans or asparagus
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Recommended snack items",
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
                   • Greek yogurt (unsweetened) with a handful of mixed berries (blueberries, strawberries)
                   • A handful of almonds (about 10-12 nuts) 
                   • 1 small orange or 1/2 cup of mixed veggies (e.g., cucumber, bell peppers)
                   • 1 small piece of dark chocolate (70% cacao or higher) or a handful of walnuts
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 16.sp
                            )
                        } else {
                            Text(
                                text = "will be updated soon",
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}

