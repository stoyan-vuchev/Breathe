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
fun FitnessNormalWeightScreen(
    fitnessState: FitnessScreenState,
    onEditClick: () -> Unit // callback when edit button is pressed
) {
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
                .padding(top = 100.dp, bottom = 70.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Top header with Edit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
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

            }
            Spacer(modifier = Modifier.height(24.dp))
            // BMI Condition Box (Status)
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
                        text = "Normal",
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
                        .height(138.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(2.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(16.dp)) // Add border here
                        .padding(20.dp)
                ){
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (dayNumber == 1L) {
                            Text(
                                text ="""
                  Protein: Aim for 20-30g of protein per meal to support muscle repair and growth.
                  Carbohydrates: Include complex carbs like whole grains, quinoa, brown rice, and sweet potatoes to fuel your workouts and maintain energy levels.
                  Healthy Fats: Include sources like olive oil, avocado, nuts, and fatty fish to support joint health and overall wellness.
                  Fiber: Ensure each meal contains plenty of vegetables, fruits, or whole grains to aid digestion and maintain energy.
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 16.sp
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
                                text ="""
                   Day 1: Full-Body Strength and Core 
1. Warm-up (5-10 minutes): 
o Jumping jacks 
o Arm circles 
o Leg swings 
o High knees 
2. Workout (3 sets of each exercise, 12-15 reps unless noted): 
o Squats (Bodyweight or with resistance band around thighs for added tension) 
o Dumbbell Shoulder Press (if you have dumbbells) 
o Glute Bridges (Add resistance band around thighs to make it harder) 
o Superman Hold (Hold for 30-60 seconds) 
o Plank (Hold for 30-45 seconds) 
o Lunges (Bodyweight or with dumbbells) 
3. Cooldown (5-10 minutes): 
o Full-body stretching, focusing on legs, chest, back, and arms.
                            """.trimIndent(),
                                style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                fontSize = 16.sp
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
                                    text ="""
                        Breakfast: 
Option 1: Veggie Omelet with Whole Grain Toast 
• 2-3 eggs (or egg whites if preferred) 
• Spinach, tomatoes, onions, and bell peppers (sautéed or fresh) 
• 1 slice of whole-grain toast 
• 1 teaspoon of olive oil for cooking 
• A small portion of avocado (optional) 
Option 2: Greek Yogurt Parfait 
• 1 cup plain Greek yogurt (high-protein) 
• 1/2 cup mixed berries (strawberries, blueberries, raspberries) 
• 1 tablespoon chia seeds or flax seeds 
• A small handful of granola or nuts for crunch 
Option 3: Smoothie 
• 1 scoop protein powder (optional) 
• 1 banana 
• 1 tablespoon peanut or almond butter 
• 1 cup unsweetened almond milk or water 
• A handful of spinach or kale (optional) 
• 1 tablespoon chia seeds
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 16.sp
                                )
                            }
                            "Lunch" -> {
                                Text(
                                    text ="""
                       Option 1: Grilled Chicken Salad 
• 4-6 oz grilled chicken breast 
• Mixed greens (spinach, arugula, or romaine) 
• Cherry tomatoes, cucumber, red onion, and bell peppers 
• 1 tablespoon olive oil and balsamic vinegar for dressing 
• 1/4 cup quinoa or brown rice (optional for added carbs) 
Option 2: Quinoa Bowl with Veggies 
• 1/2 cup cooked quinoa 
• 1/2 cup roasted vegetables (sweet potatoes, carrots, broccoli, or zucchini) 
• 1/4 cup chickpeas or black beans 
• 1-2 tablespoons tahini dressing or lemon vinaigrette 
• 1/4 avocado 
Option 3: Turkey and Hummus Wrap 
• Whole grain or lettuce wrap 
• 4 oz lean turkey breast (or grilled chicken) 
• 2 tablespoons hummus 
• Mixed greens, cucumbers, and tomatoes 
• 1 slice of low-fat cheese (optional) 
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 16.sp
                                )
                            }
                            else -> { // Dinner
                                Text(
                                    text ="""
                       Dinner: 
Option 1: Baked Salmon with Veggies and Sweet Potato 
• 4-6 oz salmon fillet (rich in omega-3 fatty acids) 
• 1 medium sweet potato, baked or roasted 
• Steamed or roasted veggies (broccoli, cauliflower, asparagus, or Brussels sprouts) 
• 1 tablespoon olive oil for cooking 
Option 2: Stir-Fried Tofu or Chicken with Veggies and Brown Rice 
• 4 oz grilled chicken or tofu 
• Mixed vegetables (bell peppers, mushrooms, onions, broccoli, and carrots) 
• 1/2 cup cooked brown rice or quinoa 
• 1 tablespoon soy sauce (low sodium) or coconut aminos 
• 1 teaspoon sesame oil for stir-frying 
Option 3: Lean Beef or Turkey Chili 
• 4 oz lean ground beef or turkey 
• 1/2 cup kidney beans or black beans 
• 1/2 cup diced tomatoes 
• Chopped onions, bell peppers, and garlic 
• 1 tablespoon chili powder, cumin, and smoked paprika for seasoning 
• Serve with a small side of avocado or a few whole-grain crackers 
                                """.trimIndent(),
                                    style = BreathTheme.typography.bodyMedium.copy(color = Color.White),
                                    fontSize = 16.sp
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
                                text ="""
                   Snack: 
• Protein Snack: Hard-boiled eggs (2 eggs) with a handful of cherry tomatoes or 
cucumber slices. 
• Nut Butter & Fruit: 1 tablespoon almond or peanut butter with apple slices or celery. 
• Trail Mix: A handful of mixed nuts (almonds, walnuts) and dried fruit (without added 
sugar). 
• Greek Yogurt and Berries: A small bowl of plain Greek yogurt topped with a few fresh 
berries. 
• Carrot and Hummus: Baby carrots with 2 tablespoons of hummus.
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


